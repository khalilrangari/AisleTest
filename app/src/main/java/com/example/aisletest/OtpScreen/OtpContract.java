package com.example.aisletest.OtpScreen;

public interface OtpContract {

  interface OtpView {

    void showErrorMsg(String errorMsg);

    void hideProgressBar();

    void showProgressBar();

    void openHomeScreen(String token);

    void updateTimer(String time);

    void timerFinish();
  }

  interface OtpPresenter {
    void validateOtp(String mobileNumber, String countryCode, String otp);

    void startTimer();

    void validateMobileField(String mobile, String countryCode);
  }
}
