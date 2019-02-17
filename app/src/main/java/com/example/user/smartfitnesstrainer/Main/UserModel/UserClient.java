package com.example.user.smartfitnesstrainer.Main.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @POST("login")
    Call<User> login(@Body Login login);
    @GET("secret")
    Call<ResponseBody> getProfile(@Header("Content-Type") String content_type,@Header("Authorization") String authToken);
}
