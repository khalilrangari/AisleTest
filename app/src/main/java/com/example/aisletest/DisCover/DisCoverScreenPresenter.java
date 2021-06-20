package com.example.aisletest.DisCover;

import android.content.Context;

import com.example.aisletest.DisCover.ResponseModel.ProfileListResponseModel;
import com.example.aisletest.R;
import com.example.aisletest.Utility.Utility;
import com.example.aisletest.networkService.APIClient;
import com.example.aisletest.networkService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aisletest.Utility.AppConstants.COOKIE;
import static com.example.aisletest.Utility.AppConstants.OTP_EXPR_TIME;

public class DisCoverScreenPresenter implements DisCoverContract.DisCoverPresenter {

  private DisCoverContract.DisCoverView disCoverView;
  private Context mContext;
  private APIInterface apiInterface;
  private int counter = OTP_EXPR_TIME;

  DisCoverScreenPresenter(Object view, Context context) {
    disCoverView = (DisCoverContract.DisCoverView) view;
    this.mContext = context;
    this.apiInterface = APIClient.getClient().create(APIInterface.class);
  }

  @Override
  public void getProfileList(String authToken) {
    if (Utility.isInternetAvailable(mContext)) {
      disCoverView.showProgressBar();
      Call<ProfileListResponseModel> call = apiInterface.getProfileList(authToken, COOKIE);
      call.enqueue(new Callback<ProfileListResponseModel>() {
        @Override
        public void onResponse(Call<ProfileListResponseModel> call, Response<ProfileListResponseModel> response) {
          disCoverView.hideProgressBar();
          if (response !=null && response.isSuccessful()){
            if (response.body() != null){
              disCoverView.setUpData(response.body());
            }else {
              disCoverView.showErrorMsg(mContext.getResources().getString(R.string.somethingWrong));
            }
          }else {
            disCoverView.showErrorMsg(mContext.getResources().getString(R.string.somethingWrong));
          }
        }

        @Override
        public void onFailure(Call<ProfileListResponseModel> call, Throwable t) {
          disCoverView.showErrorMsg(t.getMessage());
          disCoverView.hideProgressBar();
        }
      });
    } else {
      disCoverView.showErrorMsg("Please check your Internet Connection");
      disCoverView.hideProgressBar();
    }
  }

}
