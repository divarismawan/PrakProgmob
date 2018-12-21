package com.example.fx504.praktikum.api;

import com.example.fx504.praktikum.model.ResGetById;
import com.example.fx504.praktikum.model.ResShowNovel;
import com.example.fx504.praktikum.model.RespAddFavorite;
import com.example.fx504.praktikum.model.RespAddNovel;
import com.example.fx504.praktikum.model.RespDeleteFav;
import com.example.fx504.praktikum.model.RespFavMember;
import com.example.fx504.praktikum.model.ResponseApi;
import com.example.fx504.praktikum.model.ResponseLogin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @GET("my_api")
    Call<ResponseApi>CheckConncetion();

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

    @POST("selectNovel")
    Call<List<ResShowNovel>>getNovelList();

    @GET("selectById/{id}")
    Call<ResGetById>NovelGetById(@Path("id")int id);

    @GET("favNovel")
    Call<List<RespFavMember>>getFavNovel();

    @FormUrlEncoded
    @POST("addFavorite")
    Call<RespAddFavorite>AddFavorite(@Field("id_novel") int id_novel,
                                     @Field("id_member") int id_member);

    @FormUrlEncoded
    @POST("deleteFavorite")
    Call<RespDeleteFav>DeleteFav(@Field("id_novel") int id_novel,
                                 @Field("id_member") int id_member);

}
