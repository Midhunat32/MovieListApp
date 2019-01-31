package com.example.movielistapp.presenter;


import com.example.movielistapp.cloud.responsemodel.MainResponse;

public interface MoviesListPresenter {
    interface View{
        void onShowAllMoviesList(MainResponse list);
        void onFailureFecthAllMovies(String errMsg);
    }
    interface Presenter{
        void fetchAllMovies(View mView);
    }
}
