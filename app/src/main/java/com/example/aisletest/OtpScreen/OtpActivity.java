package com.example.aisletest.OtpScreen;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aisletest.HomeScreen.HomeActivity;
import com.example.aisletest.R;
import com.example.aisletest.Utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.aisletest.Utility.AppConstants.COUNTRY_CODE;
import static com.example.aisletest.Utility.AppConstants.INTERNET_PERMISSION_REQUEST_CODE;
import static com.example.aisletest.Utility.AppConstants.MOBILE_NUMBER;
import static com.example.aisletest.Utility.AppConstants.TOKEN;

public class OtpActivity extends AppCompatActivity implements OtpContract.OtpView {


  @BindView(R.id.progressBar)
  ProgressBar progressBar;
  @BindView(R.id.tvTime)
  TextView tvTime;
  @BindView(R.id.btnContinue)
  TextView btnContinue;
  @BindView(R.id.etOtp)
  EditText etOtp;
  @BindView(R.id.ivEditNumber)
  ImageView ivEditNumber;
  @BindView(R.id.tvMobileNo)
  TextView tvMobileNo;

  private OtpContract.OtpPresenter otpPresenter;
  private String mobile, countryCode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_otp);
    ButterKnife.bind(this);
    otpPresenter = new OtpScreenPresenter(OtpActivity.this, OtpActivity.this);
    otpPresenter.startTimer();
    getIntentData();
  }

  private void getIntentData() {
    Intent intent = getIntent();
    countryCode = intent.getStringExtra(COUNTRY_CODE);
    mobile = intent.getStringExtra(MOBILE_NUMBER);
    tvMobileNo.setText(countryCode.concat(" ").concat(mobile));
  }

  @OnClick({R.id.ivEditNumber, R.id.btnContinue, R.id.tvTime})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ivEditNumber:
        onBackPressed();
        break;
      case R.id.btnContinue:
        otpPresenter.validateOtp(mobile, countryCode, etOtp.getText().toString());
        break;
      case R.id.tvTime:
        if (tvTime.getText() != null &&
            tvTime.getText().toString().equals(getResources().getString(R.string.resend))) {
          if (Utility.checkPermission(this, Manifest.permission.INTERNET, INTERNET_PERMISSION_REQUEST_CODE)) {
            otpPresenter.validateMobileField(mobile, countryCode);
          }
        }
        break;
    }
  }

  @Override
  public void showErrorMsg(String errorMsg) {
    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void hideProgressBar() {
    progressBar.setVisibility(View.GONE);
    etOtp.setEnabled(true);
    ivEditNumber.setEnabled(true);
    ivEditNumber.setClickable(true);
    btnContinue.setEnabled(true);
    btnContinue.setClickable(true);
  }

  @Override
  public void showProgressBar() {
    progressBar.setVisibility(View.VISIBLE);
    etOtp.setEnabled(false);
    ivEditNumber.setEnabled(false);
    ivEditNumber.setClickable(false);
    btnContinue.setEnabled(false);
    btnContinue.setClickable(false);
  }

  @Override
  public void openHomeScreen(String token) {
    Intent openOtpScreenIntent = new Intent(OtpActivity.this, HomeActivity.class);
    openOtpScreenIntent.putExtra(TOKEN, token);
    startActivity(openOtpScreenIntent);
    finish();
  }

  @Override
  public void updateTimer(String time) {
    tvTime.setText(time);
  }

  @Override
  public void timerFinish() {
    tvTime.setText(getResources().getString(R.string.resend));
  }
}