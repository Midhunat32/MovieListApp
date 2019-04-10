package com.example.movielistapp.cloud;


import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CloudManager {
    //https://api.themoviedb.org/3/movie/popular?api_key=c89f8bf7f35b5b719c2031faf290efeb
    @GET("3/movie/changes")
    Call<MainResponse> getAllMoviesId(@Query("api_key") String apiKey, @Query("page")String page);

    @GET("3/movie/{movie_id}")
    Call<MovieDetailsModel> getMovieDetails(@Path("movie_id")String movieId,
                                            @Query("api_key") String apiKey,
                                            @Query("language")String language);

    @GET("3/movie/changes")
    Observable<MainResponse> getAllMoviesIdRx(@Query("api_key") String apiKey, @Query("page")String page);
}
