package com.example.movielistapp.Movies;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.ui.activity.BaseActivity;
import com.example.movielistapp.ui.adapters.MoviesListAdapterV2;
import com.example.movielistapp.utility.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends BaseActivity implements ClickListener, MoviesPresenter.View,
SwipeRefreshLayout.OnRefreshListener{

    RecyclerView mRvMoviesList;
    MoviesListAdapterV2 adapter;
    MoviesPresenter.Presenter presenter;
    List<DummyModel> listDummyData = new ArrayList<>();
    DummyModel dummyModel;
    LayoutAnimationController animation;
    SwipeRefreshLayout swipeRefresh;
    List<DummyModel> dummyModelListFinal=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
        iniUi();
        registerListener();
        fetchAllIdFromCloud();
      //  test();
    }

    private void test() {
        List<DummyModel> dummyModelListFinal=new ArrayList<>();

        for (int i = 0;i<5;i++){
            DummyModel model =new DummyModel();
            model.setId(""+i);
            DataItemModel data =new DataItemModel();
            data.setTitle("Title "+i);
            model.setData(data);
            dummyModelListFinal.add(model);
        }

        DummyModel model =new DummyModel();
        model.setId("xxxxx");
        DataItemModel data =new DataItemModel();
        data.setTitle("Title xxxxx");
        model.setData(data);
        dummyModelListFinal.set(0,model);


        for (DummyModel ignored :dummyModelListFinal){
            System.out.println("TITLE "+ignored.getData().getTitle());
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
        if (moviesIdList != null) {
            for (int i = 0; i < moviesIdList.size(); i++) {
                dummyModel = new DummyModel();
                dummyModel.setId(String.valueOf(moviesIdList.get(i).getId()));
                listDummyData.add(dummyModel);
            }
            presenter.fetchMoviesDetails(this, listDummyData);
        }
    }


    @Override
    public void onFailure(String errMsg) {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMovieDetailList(final List<DummyModel> dummyModelList) {
        this.dummyModelListFinal= dummyModelList;
        if (swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }
        adapter.setMovieDataList(dummyModelListFinal);
    }

    @Override
    public void showMovieDetailItem(DummyModel object,int position) {
        if (swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }
        adapter.setMovieDataItem(object,position);
    }

    @Override
    public void onItemClicked(Object item, int position, View view) {

    }

    @Override
    public void onRefresh() {
        listDummyData.clear();
        dummyModelListFinal.clear();
        presenter.refresList();
        adapter.clearData();
        swipeRefresh.setRefreshing(true);
        presenter.fetchMoviesId(this);
    }
}
