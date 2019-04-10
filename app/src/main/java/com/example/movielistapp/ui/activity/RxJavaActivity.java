package com.example.movielistapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielistapp.BuildConfig;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.AppCloudClient;
import com.example.movielistapp.cloud.CloudManager;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.presenter.RxJavaPresenter;
import com.example.movielistapp.presenter.RxJavaPresenterImpl;


import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity implements RxJavaPresenter.View {

    RxJavaPresenter.Presenter presenter;
    ProgressBar progress;
    private Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        initObject();
        initUi();
        //dataFromCloud();
        //rxObserver();

        rxRetrofit();
    }

    private void rxRetrofit() {
        AppCloudClient.getClient().create(CloudManager.class)
           .getAllMoviesIdRx(BuildConfig.API_KEY, "1")
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Observer<MainResponse>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onNext(MainResponse mainResponse) {

               }

               @Override
               public void onError(Throwable e) {

               }

               @Override
               public void onComplete() {

               }
           });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (subscription!=null){
            subscription.cancel();
        }
    }

    private void rxObserver() {
        Observable  observable = Observable.create(emitter -> {
            emitter.onNext(5);
            emitter.onComplete();
        });

        Observer observer = new Observer(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    private void initUi() {
        progress = findViewById(R.id.progress);

    }

    private void initObject() {
        presenter = new RxJavaPresenterImpl(this);
    }

    private void dataFromCloud() {
        progress.setVisibility(View.VISIBLE);
        presenter.getAllId(this);
    }


    @Override
    public void onSuccess(List<Result> list) {
        progress.setVisibility(View.GONE);
        TextView tvList = findViewById(R.id.tvList);
        tvList.setText(""+list);
    }
}
