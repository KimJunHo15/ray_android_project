package com.example.git_test;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Multipart
    @POST("/posting")
    Call<API> addPosting(@Header("Authorization") String AccessToken,
                         @Part MultipartBody.Part photo,
                         @Part("content") RequestBody content);
}
