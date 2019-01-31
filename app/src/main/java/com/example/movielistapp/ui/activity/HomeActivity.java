package com.example.movielistapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.example.movielistapp.R;
import com.example.movielistapp.cloud.Result;
import com.example.movielistapp.cloud.responsemodel.MainResponse;
import com.example.movielistapp.presenter.MoviesListPresenter;
import com.example.movielistapp.presenter.MoviesListPresenterImpl;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class HomeActivity extends BaseActivity implements MoviesListPresenter.View, ClickListener {
    MoviesListPresenter.Presenter mPresenter;
    RecyclerView mRvMoviesList;
    List<Result>movieslist;
    Result movieSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initObjects();
        initUi();
        registerClickListener();
        fetchAllMovies();


    }

    private void initObjects() {
        mPresenter = new MoviesListPresenterImpl(this);
    }

    private void fetchAllMovies() {
        if (isNetworkAvailable())
        mPresenter.fetchAllMovies(this);
        else
            Toast.makeText(this, getString(R.string.err_network), Toast.LENGTH_SHORT).show();
    }

    private void registerClickListener() {

    }

    private void initUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
    }

    @Override
    public void onShowAllMoviesList(List<MainResponse> list) {

    }

    @Override
    public void onFailureFecthAllMovies(String errMsg) {

    }

    @Override
    public void onItemClicked(Object item, int position, View view) {

    }
}
