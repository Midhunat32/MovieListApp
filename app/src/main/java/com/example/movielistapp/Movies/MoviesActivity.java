package com.example.movielistapp.Movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;
import com.example.movielistapp.ui.activity.BaseActivity;
import com.example.movielistapp.ui.adapters.MoviesListAdapter;
import com.example.movielistapp.utility.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends BaseActivity implements ClickListener,MoviesPresenter.View {

    RecyclerView mRvMoviesList;
    MoviesListAdapter adapter;
    MoviesPresenter.Presenter presenter;

    List<DummyModel> listDummyData =new ArrayList<>();
    List<DummyModel> listDummyDataAdapter =new ArrayList<>();
    DummyModel dummyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniUi();
        initObjects();
        presenter.fetchMoviesId(this);

    }


    public void iniUi() {
        mRvMoviesList = findViewById(R.id.rvMoviesList);
        mRvMoviesList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }


    public void initObjects() {
        adapter = new MoviesListAdapter(this, this);
        presenter = new MoviesPresenterImpl(this);
    }

    @Override
    public void onItemClicked(Object item, int position, View view) {

    }

    @Override
    public void onMoviesIdList(List<Result> moviesIdList) {
        if (moviesIdList!=null){
            for (int i=0;i<moviesIdList.size();i++){
                dummyModel =new DummyModel();
                dummyModel.setId(String.valueOf(moviesIdList.get(i).getId()));
                listDummyData.add(dummyModel);
            }
            presenter.fetchMoviesDetails(this,listDummyData);
        }
    }


    @Override
    public void onFailure(String errMsg) {

    }

    @Override
    public void showMovieDetailList(final List<DummyModel> dummyModelList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setMovieIdData(dummyModelList);

            }
        });

        presenter.fetchMoviesDetails(this,listDummyData);
    }
}
