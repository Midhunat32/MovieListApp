package com.example.movielistapp.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RxJavaPresenterImpl implements RxJavaPresenter.Presenter {
    Context mContext;
    RxJavaPresenter.View rxView;

    public RxJavaPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getAllId(RxJavaPresenter.View rxView) {
        if (rxView != null) {
            this.rxView =rxView;
            CloudManager manager = AppCloudClient.getClient().create(CloudManager.class);
            Observable<MainResponse> observable = manager.getAllMoviesIdRx(BuildConfig.API_KEY, "1");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(result -> result.getResults())
                    .subscribe(this::handleResults, this::handleError);
        }
    }


    private void handleResults(List<Result> list) {
        if (list != null && list.size() != 0) {
            rxView.onSuccess(list);
        }
    }

    private void handleError(Throwable t) {

    }
}
