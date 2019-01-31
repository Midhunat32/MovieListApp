package com.example.movielistapp.cloud;


import com.example.movielistapp.cloud.responsemodel.MainResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CloudManager {
    //https://api.themoviedb.org/3/movie/popular?api_key=c89f8bf7f35b5b719c2031faf290efeb
    @GET("3/movie/changes")
    Call<MainResponse> getAllMoviesList(@Query("api_key") String apiKey,@Query("page")String page);

}
