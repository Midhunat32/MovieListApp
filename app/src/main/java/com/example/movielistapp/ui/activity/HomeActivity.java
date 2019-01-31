package com.example.movielistapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.MainResponse;
import com.example.movielistapp.presenter.MoviesListPresenter;
import com.example.movielistapp.presenter.MoviesListPresenterImpl;
import com.example.movielistapp.ui.adapters.MoviesListAdapter;
import com.example.movielistapp.utility.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements MoviesListPresenter.View, ClickListener {
    MoviesListPresenter.Presenter mPresenter;
    RecyclerView mRvMoviesList;
    List<Result> resultsId;
    MoviesListAdapter adapter;
    List<DataItemModel> singleItemData;

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
        singleItemData = new ArrayList<>();
    }

    private void fetchAllMovies() {

        if (isNetworkAvailable())
            mPresenter.fetchAllMovieId(this);
        else
            Toast.makeText(this, getString(R.string.err_network), Toast.LENGTH_SHORT).show();

        mRvMoviesList.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

                int itemCount = adapter.getCurrentItem();
                String id = resultsId.get(itemCount).getId().toString();
                Log.e("xxx", resultsId.get(itemCount).getId().toString());
                TextView textView = view.findViewById(R.id.tvTitle);
                textView.setText(resultsId.get(itemCount).getId().toString());

                fetchMovieDetailsViewId(view, id);

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });


    }


    private void fetchMovieDetailsViewId(View viewRecycler, String idMovie) {
        if (mPresenter != null) {
            mPresenter.fetchAllMovieList(this, idMovie, viewRecycler);
        }
    }

    private void registerClickListener() {

    }

    private void initUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
        mRvMoviesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onFetchAllMoviesId(MainResponse list) {
        if (null != list) {
            resultsId = list.getResults();
           // adapter.setMovieIdData(list.getResults());
        }

    }

    private void setRecyclerData() {
        adapter = new MoviesListAdapter(this, this);
        mRvMoviesList.setAdapter(adapter);
    }

    @Override
    public void onFailureFetchAllMovieId(String errMsg) {
    }

    @Override
    public void onFetchMovieDetails(final DataItemModel data, final View viewRecycler) {
        if (null != data) {

            TextView tvTitle = viewRecycler.findViewById(R.id.tvTitle);
            TextView tvDescription = viewRecycler.findViewById(R.id.tvDescription);

            tvTitle.setText(data.getTitle());
            tvDescription.setText(data.getOverview());
            Log.d("Title>>>",data.getTitle());
            Log.d("OverView>>>",data.getOverview());
            if (null != data.getPosterPath()) {
                ImageView ivPoster = viewRecycler.findViewById(R.id.ivPoster);
                Glide.with(this)
                        .load(data.getPosterPath())
                        .into(ivPoster);

//                Picasso.get()
//                        .load(data.getPosterPath())
//                        .into(ivPoster);
            }
        }
    }


    @Override
    public void onFailureFetchMovieDetails(String errMsg) {

    }

    @Override
    public void onItemClicked(Object item, int position, View view) {

    }
}
