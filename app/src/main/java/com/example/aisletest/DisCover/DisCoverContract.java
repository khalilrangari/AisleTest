package com.example.aisletest.DisCover;

import com.example.aisletest.DisCover.ResponseModel.ProfileListResponseModel;

public interface DisCoverContract {

  interface DisCoverView {

    void showErrorMsg(String errorMsg);

    void hideProgressBar();

    void showProgressBar();

    void setUpData(ProfileListResponseModel body);
  }

  interface DisCoverPresenter {
    void getProfileList(String authToken);
  }
}
