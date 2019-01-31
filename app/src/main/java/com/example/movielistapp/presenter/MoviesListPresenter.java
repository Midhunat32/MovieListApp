package com.example.movielistapp.presenter;


import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;

public interface MoviesListPresenter {
    interface View{
        void onFetchAllMoviesId(MainResponse list);
        void onFailureFetchAllMovieId(String errMsg);

        void onFetchMovieDetails(DataItemModel data, android.view.View viewRecycler);
        void onFailureFetchMovieDetails(String errMsg);


    }
    interface Presenter{
        void fetchAllMovieId(View mView);
        void fetchAllMovieList(MoviesListPresenter.View mView, String id, android.view.View viewRecycler);
    }
}
