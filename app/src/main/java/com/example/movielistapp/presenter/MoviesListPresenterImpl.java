package com.example.movielistapp.presenter;

import android.content.Context;
import android.view.View;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListPresenterImpl implements MoviesListPresenter.Presenter {
    Context mContext;
    public static final String APIKEY= BuildConfig.API_KEY;
    public MoviesListPresenterImpl(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public void fetchAllMovieId(final MoviesListPresenter.View mView) {
        if (mView!=null){
            CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
            Call<MainResponse>call = manager.getAllMoviesId(APIKEY,"1");
            call.enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.isSuccessful()){
                        MainResponse response1 =response.body();
                        mView.onFetchAllMoviesId(response1);
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    if (null!= t.getMessage())
                    mView.onFailureFetchAllMovieId(t.getMessage());
                }
            });
        }
    }

    @Override
    public void fetchAllMovieList(final MoviesListPresenter.View mView, String idMovie,final View viewRecycler) {
        if (mView!=null){
            CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
            Call<DataItemModel>call = manager.getMovieDetails(idMovie,APIKEY);
            call.enqueue(new Callback<DataItemModel>() {
                @Override
                public void onResponse(Call<DataItemModel> call, Response<DataItemModel> response) {
                    if (response.isSuccessful()) {
                        DataItemModel data = response.body();
                        mView.onFetchMovieDetails(data,viewRecycler);
                    }
                }

                @Override
                public void onFailure(Call<DataItemModel> call, Throwable t) {
                    if (null != t.getMessage()){
                        mView.onFailureFetchMovieDetails(t.getMessage());
                    }
                }
            });
        }
    }
}
