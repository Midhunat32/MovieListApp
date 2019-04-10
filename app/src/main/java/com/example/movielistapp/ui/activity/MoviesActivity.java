package com.example.movielistapp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movielistapp.Movies.MovieItemModel;
import com.example.movielistapp.presenter.MoviesPresenter;
import com.example.movielistapp.presenter.MoviesPresenterImpl;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.ui.adapters.MoviesListAdapterV2;
import com.example.movielistapp.utility.ClickListener;
import com.example.movielistapp.utility.DisplayChecker;

import java.util.ArrayList;
import java.util.List;

import static com.example.movielistapp.Constants.ANIM_ID;
import static com.example.movielistapp.MovieAppConstants.MOVIE_SELECTED;

public class MoviesActivity extends BaseActivity implements ClickListener, MoviesPresenter.View {

    private RecyclerView mRvMoviesList;
    public static MoviesListAdapterV2 adapter;
    private MoviesPresenter.Presenter presenter;
    private List<MovieItemModel> movieItemModelListTemp = new ArrayList<>();
    private List<MovieItemModel> movieItemModelList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private String transitionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        iniUi();
        registerListener();
        fetchAllIdFromCloud();
        checkDeviceScreen();
        addRecyclerScrollListener();
    }


    private void addRecyclerScrollListener() {
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

    }


    public void iniUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
        mRvMoviesList.setAdapter(adapter);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mRvMoviesList.setLayoutAnimation(animation);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }


    public void initObjects() {
        adapter = new MoviesListAdapterV2(this, this);
        presenter = new MoviesPresenterImpl(this);

    }


    @Override
    public void onMoviesIdList(List<Result> moviesIdList) {
        swipeRefresh.setRefreshing(false);
        if (moviesIdList != null) {
            MovieItemModel movieItemModel;
            for (int i = 0; i < moviesIdList.size(); i++) {
                movieItemModel = new MovieItemModel();
                movieItemModel.setId(String.valueOf(moviesIdList.get(i).getId()));
                movieItemModelListTemp.add(movieItemModel);
            }
            presenter.fetchMoviesDetails(this, movieItemModelListTemp);
        }
    }


    @Override
    public void onFailure(String errMsg) {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this, "" + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieDetailList(final List<MovieItemModel> list) {
        swipeRefresh.setRefreshing(false);
        movieItemModelList = list;
        adapter.setMovieDataList(movieItemModelList);
    }

    @Override
    public void showMovieDetailItem(MovieItemModel object, int position, List<MovieItemModel> list) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        adapter.setMovieDataItem(object,position);
       // adapter.setMovieDataList(list);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClicked(Object item, int position, View mView) {
        transitionName = mView.getTransitionName();
        MovieDetailsModel data = (MovieDetailsModel) item;
        ImageView ivPoster = mView.findViewById(R.id.ivPoster);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, ivPoster, transitionName);
        intent.putExtra(MOVIE_SELECTED, data);
        intent.putExtra("TEST", transitionName);

        startActivity(intent, options.toBundle());
    }

}
