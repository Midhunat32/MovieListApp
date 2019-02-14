package com.example.movielistapp.Movies;

import android.os.Handler;

import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movielistapp.presenter.MoviesListPresenterImpl.APIKEY;


public class ApiManagerImpl implements ApiManager {
    List<MovieItemModel> movieItemModelList;
    final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);
    int mTotalItemCount, mCurrentItem = 0;
    String mMovieId;
    CloudManager manager;
    Call<DataItemModel> call;
    ApiManager.View managerView;
    final Handler handler = new Handler();
    private final int RUN = 1, STOP = 2;
    private String languageCode = "en-US";


    public ApiManagerImpl(ApiManager.View managerView, List<MovieItemModel> movieItemModelList) {
        this.movieItemModelList = movieItemModelList;
        mTotalItemCount = movieItemModelList.size();
        manager = AppCloudClient.getClient().create(CloudManager.class);
        this.managerView = managerView;

    }

    @Override
    public void fetchDataFromApi(int state) {


//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {

        if (state == RUN) {
            if (mCurrentItem < mTotalItemCount) {

                mMovieId = movieItemModelList.get(mCurrentItem).id;
                call = manager.getMovieDetails(mMovieId,APIKEY, languageCode);
                call.enqueue(new Callback<DataItemModel>() {
                    @Override
                    public void onResponse(Call<DataItemModel> call, Response<DataItemModel> response) {
                        DataItemModel data = response.body();
                        if (null != data) {
                            MovieItemModel movieItemModel = new MovieItemModel();
                            movieItemModel.setData(data);
                            movieItemModel.setId(mMovieId);
                            if (managerView != null)
                                managerView.onSuccesItem(movieItemModel, mCurrentItem);
                            addDelayApiFetch();
                        } else {
                            addDelayApiFetch();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataItemModel> call, Throwable t) {
                        if (null != t.getMessage()) {
                            managerView.onFailure(t.getMessage());
                            addDelayApiFetch();
                        }
                    }
                });
            }


        }

//            }
//        });


    }

    private void addDelayApiFetch() {
        mCurrentItem++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchDataFromApi(RUN);
            }
        }, 300);
    }

    @Override
    public void refreshList() {
        fetchDataFromApi(STOP);
        mCurrentItem = 0;
        mTotalItemCount = 0;
        if (movieItemModelList != null)
            movieItemModelList.clear();
        mTotalItemCount = 0;
        manager = null;
        managerView = null;
        return;
    }
}
