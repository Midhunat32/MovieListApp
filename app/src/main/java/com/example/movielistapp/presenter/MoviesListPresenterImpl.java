package com.example.movielistapp.presenter;

import android.content.Context;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.MainResponse;

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
    public void fetchAllMovies(final MoviesListPresenter.View mView) {
        if (mView!=null){
            CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
            Call<MainResponse>call = manager.getAllMoviesList(APIKEY,"1");
            call.enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.isSuccessful()){
                        MainResponse response1 =response.body();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    if (null!= t.getMessage())
                    mView.onFailureFecthAllMovies( t.getMessage().toString());
                }
            });
        }
    }
}
