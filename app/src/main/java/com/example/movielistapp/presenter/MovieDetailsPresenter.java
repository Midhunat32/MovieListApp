package com.example.movielistapp.presenter;

import com.example.movielistapp.Movies.MovieItemModel;

public interface MovieDetailsPresenter {
    public interface View{
        void showMovieDetailItem(MovieItemModel object, int position);

        void onFailureMovieDeatil(String errMsg);
    }

    public interface Presenter{
        void fetchMoviesDetails(MovieDetailsPresenter.View mView,String id);
    }
}
