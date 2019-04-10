package com.example.movielistapp.Movies;

import android.os.Handler;
import android.text.TextUtils;

import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movielistapp.presenter.MoviesListPresenterImpl.APIKEY;
import static com.example.movielistapp.ui.activity.MoviesActivity.adapter;


public class ApiManagerImpl implements ApiManager {
    private List<MovieItemModel> movieItemModelList=new ArrayList<>();
    private int mTotalItemCount, mCurrentItem = 0, mode, removedCount = 0;
    private String mMovieId, languageCode = "en-US";
    private CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
    private Call<MovieDetailsModel> call;
    private ApiManager.View managerView;
    private final Handler handler = new Handler();
    private List<MovieItemModel> movieItemModelListTemp = new ArrayList<>();


    public ApiManagerImpl(ApiManager.View managerView, List<MovieItemModel> movieItemModelList) {
        this.movieItemModelList = movieItemModelList;
        mTotalItemCount = movieItemModelList.size();
        this.managerView = managerView;
    }

    @Override
    public void fetchDataFromApi() {
        if (mCurrentItem < mTotalItemCount) {
            MovieDetailsModel model = movieItemModelList.get(mCurrentItem).getData();
            if (null == model) {
                mMovieId = movieItemModelList.get(mCurrentItem).id;
                call = manager.getMovieDetails(mMovieId, APIKEY, languageCode);
                call.enqueue(new Callback<MovieDetailsModel>() {
                    @Override
                    public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                        if (response.isSuccessful()) {
                            MovieDetailsModel data = response.body();
                            if (data == null ||
                                    (TextUtils.isEmpty(data.getOverview())) ||
                                            TextUtils.isEmpty(data.getPosterPath())){
                                movieItemModelList.set(mCurrentItem, null);
                                movieItemModelListTemp.add(null);
                                removedCount++;
                            }else {

                                movieItemModelList.get(mCurrentItem).setData(data);
                                movieItemModelList.set(mCurrentItem, movieItemModelList.get(mCurrentItem));
                                movieItemModelListTemp.add(movieItemModelList.get(mCurrentItem));
                            }
                        }else {
                            movieItemModelList.set(mCurrentItem, null);
                            movieItemModelListTemp.add(null);
                            removedCount++;
                        }
                        mode = mCurrentItem % 5;

                        while (movieItemModelListTemp.remove(null)){}

                        if (mCurrentItem != 0 && mode == 0) {
                            if (managerView!=null){
                              //  managerView.onSuccesItem(movieItemModelListTemp.get(mCurrentItem),mCurrentItem,movieItemModelListTemp);
                                adapter.setMovieDataList(movieItemModelListTemp);
                            }
                        }else if ((mCurrentItem) == (mTotalItemCount - mode) + removedCount) {
                            if (managerView!=null){
                               // managerView.onSuccesItem(null,mCurrentItem,movieItemModelListTemp);
                                adapter.setMovieDataList(movieItemModelListTemp);
                            }
                        }
                        mCurrentItem++;
                        Handler handler = new Handler();
                        handler.postDelayed(() -> addDelayApiFetch(), 100);

                    }

                    @Override
                    public void onFailure(Call<MovieDetailsModel> call, Throwable t) {


                        if (null == t.getMessage()) {
                            managerView.onFailure(t.getMessage());
                        }
                    }
                });
            }
        }
    }


    private void addDelayApiFetch() {
        fetchDataFromApi();
       // handler.postDelayed(() -> fetchDataFromApi(), 0);
    }


}
