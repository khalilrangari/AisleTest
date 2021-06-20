package com.example.aisletest.networkService;

import com.example.aisletest.DisCover.ResponseModel.ProfileListResponseModel;
import com.example.aisletest.OtpScreen.OTPRequestModel;
import com.example.aisletest.OtpScreen.OTPResponseModel;
import com.example.aisletest.PhoneNumberScreen.PhoneNumberRequestModel;
import com.example.aisletest.modles.CommonResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

  @POST("phone_number_login")
  Call<CommonResponseModel> phoneNumberLogin(@Header("Content-Type") String contentType,
                                             @Header("Cookie")  String cookie,
                                             @Body PhoneNumberRequestModel fullMobileNo);

  @POST("verify_otp")
  Call<OTPResponseModel> verifyOtp(@Header("Content-Type") String contentType,
                                   @Header("Cookie")  String cookie,
                                   @Body OTPRequestModel model);

  @GET("test_profile_list")
  Call<ProfileListResponseModel> getProfileList(@Header("Authorization") String authToken,
                                                @Header("Cookie")  String cookie);
}
