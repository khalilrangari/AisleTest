package com.example.aisletest.splashScreen;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aisletest.PhoneNumberScreen.PhoneNumberActivity;
import com.example.aisletest.R;

import static com.example.aisletest.Utility.AppConstants.SPLASH_DISPLAY_LENGTH;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

  private MainContract.MainPresenter mainPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainPresenter = new MainActivityPresenter(this);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent mainIntent = new Intent(MainActivity.this, PhoneNumberActivity.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
      }
    }, SPLASH_DISPLAY_LENGTH);

  }
}