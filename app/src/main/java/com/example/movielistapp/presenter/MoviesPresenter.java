package com.example.movielistapp.presenter;

import com.example.movielistapp.Movies.MovieItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

import java.util.List;

public interface MoviesPresenter {

    public interface View{
        void onMoviesIdList(List<Result>moviesIdList);

        void onFailure(String errMsg);

        void showMovieDetailList(List<MovieItemModel> object);

        void showMovieDetailItem(MovieItemModel object, int position,List<MovieItemModel> movieItemModelList);
    }

    public interface Presenter{

        void fetchMoviesId(MoviesPresenter.View mView);

        void fetchMoviesDetails(MoviesPresenter.View mView,List<MovieItemModel> movieItemModelList);

    }

}
