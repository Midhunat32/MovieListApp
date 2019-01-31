package com.example.movielistapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.movielistapp.R;
import com.example.movielistapp.cloud.Result;
import com.example.movielistapp.cloud.responsemodel.MainResponse;
import com.example.movielistapp.presenter.MoviesListPresenter;
import com.example.movielistapp.presenter.MoviesListPresenterImpl;
import com.example.movielistapp.ui.adapters.MoviesListAdapter;
import com.example.movielistapp.ui.fragment.MovieItemListFragment;
import com.example.movielistapp.utility.ClickListener;

import java.util.List;

public class HomeActivity extends BaseActivity implements MoviesListPresenter.View, ClickListener {
    MoviesListPresenter.Presenter mPresenter;
    RecyclerView mRvMoviesList;
    List<Result> results;
    MoviesListAdapter adapter;
    private ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initObjects();
        initUi();
        registerClickListener();
        setRecyclerData();
        fetchAllMovies();


    }

    private void initObjects() {
        mPresenter = new MoviesListPresenterImpl(this);
    }

    private void fetchAllMovies() {

        mRvMoviesList.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

                int itemCount = adapter.getCurrentItem();
                Log.e("xxx",results.get(itemCount).getId().toString());
                TextView textView = view.findViewById(R.id.tvText);
                textView.setText(results.get(itemCount).getId().toString());


            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });


        if (isNetworkAvailable())
        mPresenter.fetchAllMovies(this);
        else
            Toast.makeText(this, getString(R.string.err_network), Toast.LENGTH_SHORT).show();
    }

    private void registerClickListener() {
        listener = new ClickListener() {
            @Override
            public void onItemClicked(Object item, int position, View view) {

            }
        };

    }

    private void initUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
        mRvMoviesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onShowAllMoviesList(MainResponse list) {
        if(null != list){
            results = list.getResults();
            adapter.setData(list.getResults());
        }

    }

    private void setRecyclerData(){
        adapter = new MoviesListAdapter(getSupportFragmentManager(),this,listener);
        mRvMoviesList.setAdapter(adapter);
    }

    @Override
    public void onFailureFecthAllMovies(String errMsg){
    }

    @Override
    public void onItemClicked(Object item, int position, View view) {

    }
}
