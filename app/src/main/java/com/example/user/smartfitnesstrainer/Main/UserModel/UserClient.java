package com.example.user.smartfitnesstrainer.Main.UserModel;

import com.example.user.smartfitnesstrainer.Main.Profile.Detail_Player_History;
import com.example.user.smartfitnesstrainer.Main.Profile.UserProfile;
import com.example.user.smartfitnesstrainer.Main.exercise_selection_page.Playlist;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {
    @POST("login")
    Call<User> login(@Body Login login);
    @GET("secret")
    Call<UserProfile> getProfile(@Header("Content-Type") String content_type, @Header("Authorization") String authToken);
    @GET("playlist")
    Call<List<Playlist>> getPlaylist(@Header("Content-Type") String content_type, @Header("Authorization") String authToken);
    @GET("playlist/{id}")
    Call<ResponseBody> getPlaylistWithid(@Header("Content-Type") String content_type, @Header("Authorization") String authToken,@Path("id") int id);
    @POST("postrecord")
    Call<ResponseBody> postRecord(@Header("Content-Type") String content_type, @Header("Authorization") String authToken, @Body RequestBody body);
    @GET("getrecord/{id}")
    Call<Detail_Player_History> getRecord(@Header("Content-Type") String content_type, @Header("Authorization") String authToken, @Path("id") int id);
}
