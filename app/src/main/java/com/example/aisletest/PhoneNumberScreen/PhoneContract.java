package com.example.aisletest.PhoneNumberScreen;

import com.techreinforce.countypickerlibrary.CountryPicker;

public interface PhoneContract {

  interface PhoneView {

    void setUpCountryCode(int flagDrawableResID, String dialCode, int max, boolean b);

    void showErrorMsg(String errorMsg);

    void hideProgressBar();

    void showProgressBar();

    void openOtpScreen(String countryCode, String mobileNumber);
  }

  interface PhonePresenter {
    void getCountryInfo(CountryPicker countryPicker);

    void addListenerForCountry(CountryPicker countryPicker);

    void validateMobileField(String mobileNumber, String countryCode);
  }
}
