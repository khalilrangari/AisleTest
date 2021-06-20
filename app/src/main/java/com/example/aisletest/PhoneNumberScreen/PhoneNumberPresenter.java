package com.example.aisletest.PhoneNumberScreen;

import android.content.Context;
import android.util.Log;

import com.example.aisletest.R;
import com.example.aisletest.Utility.Utility;
import com.example.aisletest.modles.CommonResponseModel;
import com.example.aisletest.networkService.APIClient;
import com.example.aisletest.networkService.APIInterface;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techreinforce.countypickerlibrary.Country;
import com.techreinforce.countypickerlibrary.CountryPicker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aisletest.Utility.AppConstants.CONTENT_TYPE;
import static com.example.aisletest.Utility.AppConstants.COOKIE;

public class PhoneNumberPresenter implements PhoneContract.PhonePresenter {

  private PhoneContract.PhoneView phoneView;
  private Context mContext;
  private APIInterface apiInterface;

  PhoneNumberPresenter(Object view, Context context) {
    phoneView = (PhoneNumberActivity) view;
    this.mContext = context;
    this.apiInterface = APIClient.getClient().create(APIInterface.class);
  }

  @Override
  public void getCountryInfo(CountryPicker countryPicker) {
    Country country = countryPicker.getUserCountryInfo(mContext);
    phoneView.setUpCountryCode(country.getFlag(), country.getDialCode(), country.getMaxDigits()
        , true);
  }

  @Override
  public void addListenerForCountry(CountryPicker countryPicker) {
    countryPicker.setListener((name, code, dialCode, flagDrawableResID, min, max) ->
    {
      phoneView.setUpCountryCode(flagDrawableResID, dialCode, max, false);
    });

  }

  @Override
  public void validateMobileField(String mobileNumber, String countryCode) {
    if (mobileNumber != null && !mobileNumber.isEmpty()) {
      if (Utility.checkMobileNumberReceiver(countryCode, mobileNumber)) {
        validateMobileAvailability(mobileNumber, countryCode);
      } else {
        phoneView.showErrorMsg(mContext.getResources().getString(R.string.inValidMobileNumber));
      }
    } else {
      phoneView.showErrorMsg(mContext.getResources().getString(R.string.emptyMobileNumber));
    }
  }

  private void validateMobileAvailability(String mobileNumber, String countryCode) {
    if (Utility.isInternetAvailable(mContext)) {
      phoneView.showProgressBar();
      Log.e("PhoneNumberPresenter", "CountryCode: " + countryCode + " phoneNumber: " + mobileNumber);

      String fullMobileNo = countryCode + mobileNumber;
      PhoneNumberRequestModel model = new PhoneNumberRequestModel();
      model.setNumber(fullMobileNo);

      Call<CommonResponseModel> call = apiInterface.phoneNumberLogin(CONTENT_TYPE, COOKIE, model);
      call.enqueue(new Callback<CommonResponseModel>() {
        @Override
        public void onResponse(@NotNull Call<CommonResponseModel> call, @NotNull Response<CommonResponseModel> response) {
          phoneView.hideProgressBar();
          if (response != null && response.isSuccessful()) {
            if (response.body() != null && response.body().getStatus()) {
              phoneView.openOtpScreen(countryCode, mobileNumber);
            } else {
              phoneView.showErrorMsg("Please Enter This Mobile No. +919876543212");
            }
          }
        }

        @Override
        public void onFailure(@NotNull Call<CommonResponseModel> call, @NotNull Throwable t) {
          phoneView.showErrorMsg("Something is wrong please try gain");
          phoneView.showProgressBar();
        }
      });
    } else {
      phoneView.showErrorMsg("Please check your Internet Connection");
      phoneView.hideProgressBar();
    }
  }
}
