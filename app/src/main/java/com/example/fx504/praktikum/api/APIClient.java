package com.example.fx504.praktikum.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static APIService getService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }
}
