package com.example.movielistapp.presenter;


import com.example.movielistapp.cloud.responsemodel.Result;

import java.util.List;

public interface MoviesListPresenter {
    interface View{
        void onShowAllMoviesList(List<Result> list);
        void onFailureFecthAllMovies(String errMsg);
    }
    interface Presenter{
        void fetchAllMovies(View mView);
    }
}
