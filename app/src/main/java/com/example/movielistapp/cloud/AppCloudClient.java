package com.example.movielistapp.cloud;

import com.example.movielistapp.BuildConfig;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppCloudClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
