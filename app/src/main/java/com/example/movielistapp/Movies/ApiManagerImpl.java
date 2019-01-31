package com.example.movielistapp.Movies;

import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movielistapp.presenter.MoviesListPresenterImpl.APIKEY;

public class ApiManagerImpl implements ApiManager {


    @Override
    public void fetchDataFromApi(final ApiManager.View viewmanager, final List<DummyModel> listDummyModel) {

        final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                if (listDummyModel == null || listDummyModel.size()<=0){
                    return;
                }
                List<DummyModel>queue=listDummyModel;
                if (listDummyModel.get(listDummyModel.size()-1).data!=null){
                    listDummyModel.remove(listDummyModel.size()-1);
                }else {
                    String movieId = listDummyModel.get(listDummyModel.size()-1).id;
                    CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
                    Call<DataItemModel> call = manager.getMovieDetails(movieId,APIKEY);
                    call.enqueue(new Callback<DataItemModel>() {
                        @Override
                        public void onResponse(Call<DataItemModel> call, Response<DataItemModel> response) {
                            if (response.isSuccessful()) {
                                DataItemModel data = response.body();//raw data from server
                                DummyModel dummyModel =new DummyModel();
                                dummyModel.setData(data);
                                listDummyModel.add(listDummyModel.size()-1,dummyModel);
                                viewmanager.onSuccess(listDummyModel);
                            }
                        }

                        @Override
                        public void onFailure(Call<DataItemModel> call, Throwable t) {
                            if (null != t.getMessage()){
                                viewmanager.onFailure(t.getMessage());
                            }
                        }
                    });
                }
            }
        });
        thread.start();


//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                if (listDummyModel == null || listDummyModel.size()<=0){
//                    return;
//                }
//                List<DummyModel>queue=listDummyModel;
//                if (listDummyModel.get(listDummyModel.size()-1).data!=null){
//                    listDummyModel.remove(listDummyModel.size()-1);
//                }else {
//                    String movieId = listDummyModel.get(listDummyModel.size()-1).id;
//                    CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
//                    Call<DataItemModel> call = manager.getMovieDetails(movieId,APIKEY);
//                    call.enqueue(new Callback<DataItemModel>() {
//                        @Override
//                        public void onResponse(Call<DataItemModel> call, Response<DataItemModel> response) {
//                            if (response.isSuccessful()) {
//                                DataItemModel data = response.body();//raw data from server
//                                DummyModel dummyModel =new DummyModel();
//                                dummyModel.setData(data);
//                                listDummyModel.add(listDummyModel.size()-1,dummyModel);
//                                viewmanager.onSuccess(listDummyModel);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DataItemModel> call, Throwable t) {
//                            if (null != t.getMessage()){
//                                viewmanager.onFailure(t.getMessage());
//                            }
//                        }
//                    });
//                }
//
//            }
//        });

    }
}
