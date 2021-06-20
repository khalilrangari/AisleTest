package com.example.aisletest.OtpScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.aisletest.PhoneNumberScreen.PhoneNumberRequestModel;
import com.example.aisletest.R;
import com.example.aisletest.Utility.Utility;
import com.example.aisletest.modles.CommonResponseModel;
import com.example.aisletest.networkService.APIClient;
import com.example.aisletest.networkService.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aisletest.Utility.AppConstants.CONTENT_TYPE;
import static com.example.aisletest.Utility.AppConstants.COOKIE;
import static com.example.aisletest.Utility.AppConstants.OTP_EXPR_TIME;
import static com.example.aisletest.Utility.AppConstants.OTP_TIME_INTERVAL;

public class OtpScreenPresenter implements OtpContract.OtpPresenter {

  private OtpContract.OtpView otpView;
  private Context mContext;
  private APIInterface apiInterface;
  private int counter = OTP_EXPR_TIME;

  OtpScreenPresenter(Object view, Context context) {
    otpView = (OtpActivity) view;
    this.mContext = context;
    this.apiInterface = APIClient.getClient().create(APIInterface.class);
  }

  @Override
  public void validateOtp(String mobileNumber, String countryCode, String otp) {
    if (otp != null && otp.length() == 4) {
      callVerifyOtpApi(mobileNumber, countryCode, otp);
    } else {
      otpView.showErrorMsg(mContext.getResources().getString(R.string.inValidLengthOtp));
    }
  }

  private void callVerifyOtpApi(String mobileNumber, String countryCode, String otp) {
    if (Utility.isInternetAvailable(mContext)) {
      otpView.showProgressBar();
      Log.e("PhoneNumberPresenter", "CountryCode: " + countryCode + " phoneNumber: " + mobileNumber);

      String fullMobileNo = countryCode + mobileNumber;
      OTPRequestModel model = new OTPRequestModel();
      model.setNumber(fullMobileNo);
      model.setOtp(otp);

      Call<OTPResponseModel> call = apiInterface.verifyOtp(CONTENT_TYPE, COOKIE, model);
      call.enqueue(new Callback<OTPResponseModel>() {
        @Override
        public void onResponse(@NotNull Call<OTPResponseModel> call, @NotNull Response<OTPResponseModel> response) {
          otpView.hideProgressBar();
          if (response != null && response.isSuccessful()) {
            if (response.body() != null && response.body().getToken() != null
            && !response.body().getToken().isEmpty()) {
              Log.e("OtpScreenPresenter","authToken : "+response.body().getToken());
              otpView.openHomeScreen(response.body().getToken());
            } else {
              otpView.showErrorMsg(mContext.getResources().getString(R.string.inValidOtp));
            }
          }
        }

        @Override
        public void onFailure(@NotNull Call<OTPResponseModel> call, @NotNull Throwable t) {
          otpView.showErrorMsg("Something is wrong please try gain");
          otpView.showProgressBar();
        }
      });
    } else {
      otpView.showErrorMsg("Please check your Internet Connection");
      otpView.hideProgressBar();
    }
  }

  @Override
  public void startTimer() {
    new CountDownTimer(OTP_EXPR_TIME, OTP_TIME_INTERVAL) {
      public void onTick(long millisUntilFinished) {
        convertTime(millisUntilFinished/1000);
//        otpView.updateTimer(String.valueOf(millisUntilFinished / 1000));
      }

      public void onFinish() {
        otpView.timerFinish();
      }
    }.start();
  }

  private void convertTime(long miliSec) {
    long s = miliSec % 60;
    long m = (miliSec / 60) % 60;
    long h = (miliSec / (60 * 60)) % 24;
    otpView.updateTimer(String.format("%02d:%02d", m,s));
  }


  @Override
  public void validateMobileField(String mobileNumber, String countryCode) {
    if (mobileNumber != null && !mobileNumber.isEmpty()) {
      if (Utility.checkMobileNumberReceiver(countryCode, mobileNumber)) {
        validateMobileAvailability(mobileNumber, countryCode);
      } else {
        otpView.showErrorMsg(mContext.getResources().getString(R.string.inValidMobileNumber));
      }
    } else {
      otpView.showErrorMsg(mContext.getResources().getString(R.string.emptyMobileNumber));
    }
  }

  private void validateMobileAvailability(String mobileNumber, String countryCode) {
    if (Utility.isInternetAvailable(mContext)) {
      otpView.showProgressBar();
      Log.e("PhoneNumberPresenter", "CountryCode: " + countryCode + " phoneNumber: " + mobileNumber);

      String fullMobileNo = countryCode + mobileNumber;
      PhoneNumberRequestModel model = new PhoneNumberRequestModel();
      model.setNumber(fullMobileNo);

      Call<CommonResponseModel> call = apiInterface.phoneNumberLogin(CONTENT_TYPE, COOKIE, model);
      call.enqueue(new Callback<CommonResponseModel>() {
        @Override
        public void onResponse(@NotNull Call<CommonResponseModel> call, @NotNull Response<CommonResponseModel> response) {
          otpView.hideProgressBar();
          if (response != null && response.isSuccessful()) {
            if (response.body() != null && response.body().getStatus()) {
              startTimer();
            } else {
              otpView.showErrorMsg("Please Enter This Mobile No. +919876543215");
            }
          }
        }

        @Override
        public void onFailure(@NotNull Call<CommonResponseModel> call, @NotNull Throwable t) {
          otpView.showErrorMsg("Something is wrong please try gain");
          otpView.showProgressBar();
        }
      });
    } else {
      otpView.showErrorMsg("Please check your Internet Connection");
      otpView.hideProgressBar();
    }
  }
}
