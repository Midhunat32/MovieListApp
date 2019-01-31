package com.example.movielistapp.Movies;

import android.content.Context;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;
import com.example.movielistapp.ui.adapters.MoviesListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movielistapp.presenter.MoviesListPresenterImpl.APIKEY;

public class MoviesPresenterImpl implements MoviesPresenter.Presenter,ApiManager.View {
    Context mContext;
    MoviesPresenter.View mView;

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
    public void fetchMoviesDetails(MoviesPresenter.View mView, List<DummyModel> dummyModelList) {
        if (mView!=null){
            this.mView = mView;
            ApiManager apiManager = new ApiManagerImpl();
            apiManager.fetchDataFromApi(this,dummyModelList);
        }
    }

    //api manager callback
    @Override
    public void onSuccess(List<DummyModel> dummyModel) {
        mView.showMovieDetailList(dummyModel);
    }

    @Override
    public void onFailure(String errMsg) {

    }
}

