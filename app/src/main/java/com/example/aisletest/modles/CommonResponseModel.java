package com.example.aisletest.modles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponseModel {
  @SerializedName("status")
  @Expose
  private Boolean status;

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

}
