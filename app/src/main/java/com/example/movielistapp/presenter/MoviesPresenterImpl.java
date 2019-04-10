package com.example.movielistapp.presenter;

import android.content.Context;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.Movies.ApiManager;
import com.example.movielistapp.Movies.ApiManagerImpl;
import com.example.movielistapp.Movies.MovieItemModel;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MoviesPresenterImpl implements MoviesPresenter.Presenter, ApiManager.View {
    Context mContext;
    MoviesPresenter.View mView;
    ApiManager apiManager;
    private final int START=1;

    public MoviesPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void fetchMoviesId(final MoviesPresenter.View mView) {
        if (mView != null) {
            CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
            Call<MainResponse> call = manager.getAllMoviesId(BuildConfig.API_KEY, "1");
            call.enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.isSuccessful()) {
                        MainResponse response1 = response.body();
                        mView.onMoviesIdList(response1.getResults());
                    }
                }
                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    if (null != t.getMessage()) {
                        mView.onFailure(t.getMessage());
                    } else {
                        mView.onFailure("Error id fetching");
                    }
                }
            });
        }
    }

    @Override
    public void fetchMoviesDetails(MoviesPresenter.View mView, List<MovieItemModel> movieItemModelList) {
        if (mView!=null && null!= movieItemModelList){
            this.mView = mView;
            apiManager = new ApiManagerImpl(this, movieItemModelList);
            apiManager.fetchDataFromApi();
        }
    }


    @Override
    public void onSuccess(List<MovieItemModel> movieItemModel) {
       // mView.showMovieDetailList(movieItemModel);
    }

    @Override
    public void onFailure(String errMsg) {

    }

    @Override
    public void onSuccesItem(MovieItemModel item, int position, List<MovieItemModel> movieItemModelList) {
        mView.showMovieDetailItem(item,position,movieItemModelList);
    }

}

