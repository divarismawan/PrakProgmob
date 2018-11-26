package com.example.fx504.praktikum.api;

import com.example.fx504.praktikum.model.RespAddNovel;
import com.example.fx504.praktikum.model.ResponseApi;
import com.example.fx504.praktikum.model.ResponseLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseApi>CreateMember(@Field("user_name") String user_name,
                                  @Field("user_pass") String user_pass,
                                  @Field("user_email") String user_email,
                                  @Field("user_tlfn") String user_tlfn
                               );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin>LoginUser(@Field("user_email") String user_email,
                                 @Field("user_pass") String user_pass);

    @Multipart
    @POST("addNovel")
    Call<RespAddNovel>NewNovels(@Part("novel_title") RequestBody novel_title,
                                @Part("novel_genre") RequestBody novel_genre,
                                @Part("novel_synopsis") RequestBody novel_synopsis,
                                @Part MultipartBody.Part novel_story,
                                @Part MultipartBody.Part novel_cover);
}
