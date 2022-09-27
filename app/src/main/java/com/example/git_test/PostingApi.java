package com.example.git_test;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostingApi {
    @Multipart
    @POST("mobile/score")
    Call<PostingRes> addPosting(
                                @Part MultipartBody.Part mem_id,
                                @Part MultipartBody.Part exam_img
    );

}
