package com.example.movielistapp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.example.movielistapp.Movies.MovieItemModel;
import com.example.movielistapp.presenter.MoviesPresenter;
import com.example.movielistapp.presenter.MoviesPresenterImpl;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.ui.adapters.MoviesListAdapterV2;
import com.example.movielistapp.utility.ClickListener;
import com.example.movielistapp.utility.DisplayChecker;

import java.util.ArrayList;
import java.util.List;

import static com.example.movielistapp.MovieAppConstants.MOVIE_SELECTED;

public class MoviesActivity extends BaseActivity implements ClickListener, MoviesPresenter.View,
        SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRvMoviesList;
    MoviesListAdapterV2 adapter;
    MoviesPresenter.Presenter presenter;
    List<MovieItemModel> listDummyData = new ArrayList<>();
    MovieItemModel movieItemModel;
    LayoutAnimationController animation;
    SwipeRefreshLayout swipeRefresh;
    List<MovieItemModel> movieItemModelListFinal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        iniUi();
        registerListener();
        fetchAllIdFromCloud();
        checkDeviceScreen();
    }

    private void checkDeviceScreen() {
        boolean isTab = DisplayChecker.isDeviceTab(this);
        if (isTab) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }
    }


    private void fetchAllIdFromCloud() {
        swipeRefresh.setRefreshing(true);
        presenter.fetchMoviesId(this);
    }

    private void registerListener() {
        swipeRefresh.setOnRefreshListener(this);
    }


    public void iniUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        mRvMoviesList.setAdapter(adapter);
        int resId = R.anim.layout_animation_fall_down;
        animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mRvMoviesList.setLayoutAnimation(animation);
    }


    public void initObjects() {
        adapter = new MoviesListAdapterV2(this, this);
        presenter = new MoviesPresenterImpl(this);

    }


    @Override
    public void onMoviesIdList(List<Result> moviesIdList) {
        if (listDummyData != null)
            listDummyData.clear();
        if (moviesIdList != null) {
            for (int i = 0; i < moviesIdList.size(); i++) {
                movieItemModel = new MovieItemModel();
                movieItemModel.setId(String.valueOf(moviesIdList.get(i).getId()));
                listDummyData.add(movieItemModel);
            }
            presenter.fetchMoviesDetails(this, listDummyData);
        }
    }


    @Override
    public void onFailure(String errMsg) {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMovieDetailList(final List<MovieItemModel> movieItemModelList) {
        this.movieItemModelListFinal = movieItemModelList;
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        adapter.setMovieDataList(movieItemModelListFinal);
    }

    @Override
    public void showMovieDetailItem(MovieItemModel object, int position) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        adapter.setMovieDataItem(object, position);
    }

    @Override
    public void onItemClicked(Object item, int position, View mView) {
        DataItemModel data = (DataItemModel) item;
        ImageView ivPoster = mView.findViewById(R.id.ivPoster);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, ivPoster, "TRANS");
        intent.putExtra(MOVIE_SELECTED, data);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onRefresh() {
        presenter.refresList();
        listDummyData.clear();
        movieItemModelListFinal.clear();
        adapter.clearData();
        swipeRefresh.setRefreshing(true);
        presenter.fetchMoviesId(this);
    }
}
