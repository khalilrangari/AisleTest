package com.example.aisletest.PhoneNumberScreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aisletest.OtpScreen.OtpActivity;
import com.example.aisletest.R;
import com.example.aisletest.Utility.Utility;
import com.techreinforce.countypickerlibrary.CountryPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aisletest.Utility.AppConstants.COUNTRY_CODE;
import static com.example.aisletest.Utility.AppConstants.INTERNET_PERMISSION_REQUEST_CODE;
import static com.example.aisletest.Utility.AppConstants.MOBILE_NUMBER;

public class PhoneNumberActivity extends AppCompatActivity implements PhoneContract.PhoneView {

  @BindView(R.id.btnContinue)
  TextView btnContinue;
  @BindView(R.id.tvCountryCode)
  TextView tvCountryCode;
  @BindView(R.id.etMobileNumber)
  EditText etMobileNumber;
  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  private CountryPicker countryPicker;
  private PhoneContract.PhonePresenter phonePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone_number);
    ButterKnife.bind(this);
    countryPicker = CountryPicker.newInstance("select Country");
    phonePresenter = new PhoneNumberPresenter(PhoneNumberActivity.this, PhoneNumberActivity.this);
    phonePresenter.getCountryInfo(countryPicker);

  }

  @SuppressLint("NonConstantResourceId")
  @OnClick({R.id.btnContinue, R.id.tvCountryCode})
  public void onClick(View clickedView) {
    switch (clickedView.getId()) {
      case R.id.btnContinue:
        if (Utility.checkPermission(this, Manifest.permission.INTERNET, INTERNET_PERMISSION_REQUEST_CODE)) {
          phonePresenter.validateMobileField(etMobileNumber.getText().toString(),
              tvCountryCode.getText().toString());
        }
        break;
      case R.id.tvCountryCode:
        phonePresenter.addListenerForCountry(countryPicker);
        countryPicker.show(getSupportFragmentManager(), "select Country");
        break;
    }
  }

  @Override
  public void setUpCountryCode(int flagDrawableResID, String countryCode, int max, boolean b) {
    if (countryPicker.isVisible())
      countryPicker.dismiss();
    tvCountryCode.setText(countryCode);
  }

  @Override
  public void showErrorMsg(String errorMsg) {
    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
    btnContinue.setClickable(true);
    btnContinue.setEnabled(true);
    etMobileNumber.setEnabled(true);
  }

  @Override
  public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
    btnContinue.setClickable(false);
    btnContinue.setEnabled(false);
    etMobileNumber.setEnabled(false);
  }

  @Override
  public void openOtpScreen(String countryCode, String mobileNumber) {
    Intent openOtpScreenIntent = new Intent(PhoneNumberActivity.this, OtpActivity.class);
    openOtpScreenIntent.putExtra(COUNTRY_CODE, countryCode);
    openOtpScreenIntent.putExtra(MOBILE_NUMBER, mobileNumber);
    startActivity(openOtpScreenIntent);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {

    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == INTERNET_PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "Internet Permission Granted", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(this, "Internet Permission Denied", Toast.LENGTH_SHORT).show();
        Utility.checkPermission(this, Manifest.permission.INTERNET, INTERNET_PERMISSION_REQUEST_CODE);
      }
    }
  }
}