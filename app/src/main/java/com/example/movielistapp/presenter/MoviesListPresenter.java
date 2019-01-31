package com.example.movielistapp.presenter;


import com.example.movielistapp.cloud.responsemodel.MainResponse;

import java.util.List;

public interface MoviesListPresenter {
    interface View{
        void onShowAllMoviesList(List<MainResponse> list);
        void onFailureFecthAllMovies(String errMsg);
    }
    interface Presenter{
        void fetchAllMovies(View mView);
    }
}
