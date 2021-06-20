package com.example.aisletest.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Utility {
  public static boolean checkMobileNumberReceiver(String CountryCode, String Number) {
    String mobileNumberWithCountry = CountryCode.replaceAll(" ", "") + Number;
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    try {
      Phonenumber.PhoneNumber phoneNumberProto = phoneUtil.parse(mobileNumberWithCountry, null);
      boolean isValid = phoneUtil.isValidNumber(phoneNumberProto); // returns true if valid
      if (isValid) {
        return true;
      } else {
        return false;
      }
    } catch (NumberParseException ignored) {

      ignored.printStackTrace();
    }

    return false;
  }

  // Function to check and request permission
  public static boolean checkPermission(Context mContext, String permission, int requestCode) {
    // Checking if permission is not granted
    if (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, requestCode);
      return false;
    } else {
      return true;
    }
  }

  public static boolean isInternetAvailable(Context mContext){
    ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==  NetworkInfo.State.CONNECTED ||
        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
      return true;
    }
    else
      return false;
  }

}
