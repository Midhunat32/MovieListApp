package com.example.movielistapp.Movies;

import android.os.Handler;
import android.util.Log;

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
    List<DummyModel> dummyModelList;
    List<DummyModel> dummyModelListTemp = new ArrayList<>();
    final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);
    int mTotalItemCount, mCurrentItem = 0;
    String mMovieId;
    CloudManager manager;
    Call<DataItemModel> call;
    ApiManager.View managerView;
    final Handler handler = new Handler();


    public ApiManagerImpl(ApiManager.View managerView, List<DummyModel> dummyModelList) {
        this.dummyModelList = dummyModelList;
        mTotalItemCount = dummyModelList.size();
        manager = AppCloudClient.getClient().create(CloudManager.class);
        this.managerView = managerView;

    }

    @Override
    public void fetchDataFromApi() {

        // while (mCurrentItem <= mTotalItemCount) {

//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {

        if (mCurrentItem < mTotalItemCount) {
//                    if (mCurrentItem == 20){
//                        mCurrentItem = mTotalItemCount;
//                        return;
//                    }
            mMovieId = dummyModelList.get(mCurrentItem).id;
            call = manager.getMovieDetails(mMovieId, APIKEY, "en-US");
            call.enqueue(new Callback<DataItemModel>() {
                @Override
                public void onResponse(Call<DataItemModel> call, Response<DataItemModel> response) {
                    DataItemModel data = response.body();//raw data from server
                    if (null != data) {
                        DummyModel dummyModel = new DummyModel();
                        dummyModel.setData(data);
                        dummyModel.setId(mMovieId);
                      //  dummyModelListTemp.add(dummyModel);
                        //managerView.onSuccess(dummyModelList);
                        managerView.onSuccesItem(dummyModel,mCurrentItem);

                        mCurrentItem++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fetchDataFromApi();
                            }
                        }, 300);
                    }else {
                        mCurrentItem++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fetchDataFromApi();
                            }
                        }, 300);
                    }
                }

                @Override
                public void onFailure(Call<DataItemModel> call, Throwable t) {
                    if (null != t.getMessage()) {
                        managerView.onFailure(t.getMessage());

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCurrentItem++;
                                fetchDataFromApi();
                            }
                        }, 300);


                    }
                }
            });
        }else {
            return;
        }


//            }
//        });

        // mCurrentItem++;
        // }


    }

    @Override
    public void refreshList() {
        mCurrentItem = 0;
        mTotalItemCount = 0;
        return;
    }
}
