package com.example.aisletest.PhoneNumberScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneNumberRequestModel {
  @SerializedName("number")
  @Expose
  private String number;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}

