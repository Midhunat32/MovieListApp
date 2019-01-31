package com.example.movielistapp.Movies;

import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

import java.util.List;

public interface MoviesPresenter {

    public interface View{
        void onMoviesIdList(List<Result>moviesIdList);

        void onFailure(String errMsg);

        void showMovieDetailList(List<DummyModel> object);
    }

    public interface Presenter{
        void fetchMoviesId(MoviesPresenter.View mView);

        void fetchMoviesDetails(MoviesPresenter.View mView,List<DummyModel>dummyModelList);

    }

}
