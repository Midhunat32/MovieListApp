package com.example.movielistapp.presenter;

import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

import java.util.List;

public class RxJavaPresenter {

    public interface View{
        void onSuccess(List<Result> list);
    }

    public interface Presenter{
        void getAllId(RxJavaPresenter.View rxView);

    }
}
