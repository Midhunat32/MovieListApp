package com.example.movielistapp.presenter;


import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

public interface MoviesListPresenter {
    interface View{
        void onFetchAllMoviesId(MainResponse list);
        void onFailureFetchAllMovieId(String errMsg);

        void onFetchMovieDetails(MovieDetailsModel data, android.view.View viewRecycler);
        void fetchMovie(Result result);

        void onFailureFetchMovieDetails(String errMsg);


    }
    interface Presenter{
        void fetchAllMovieId(View mView);
        void fetchAllMovieList(MoviesListPresenter.View mView, String id, android.view.View viewRecycler);

        void fetchAllMovieList(Result result, MoviesListPresenter.View mView);
    }
}
