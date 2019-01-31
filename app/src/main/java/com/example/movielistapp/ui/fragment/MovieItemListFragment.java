package com.example.movielistapp.ui.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movielistapp.R;

public class MovieItemListFragment  extends Fragment {
    public static final String TAG = MovieItemListFragment.class.getSimpleName();
    private static final String MOVIE_ID = "movie_id";
    private ImageView imageView;
    private TextView textView;

    public static MovieItemListFragment newInstance(int id){
        MovieItemListFragment fr = new MovieItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID,id);
        return fr;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_item, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();

    }

    private void initUI() {
        View v= getView();
        if(null != v){
            textView = v.findViewById(R.id.tvMovieName);
            imageView = v.findViewById(R.id.ivMovieImage);
        }

    }
}
