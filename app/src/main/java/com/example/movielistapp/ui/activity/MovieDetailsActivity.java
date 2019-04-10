package com.example.movielistapp.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.example.movielistapp.utility.DisplayChecker;
import com.squareup.picasso.Picasso;

import static com.example.movielistapp.MovieAppConstants.IMAGE_URL;
import static com.example.movielistapp.MovieAppConstants.MOVIE_SELECTED;

public class MovieDetailsActivity extends BaseActivity {
    private TextView tvRating, tvTitle, tvOverView;
    private ImageView ivPoster;
    private int DELAY = 1000;
    private String transitionName;
    RoundCornerProgressBar roundCornerProgressBar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);



        initUi();
        displayDetails();
        ivPoster.setTransitionName(transitionName);
        checkDeviceScreen();
    }

    private void checkDeviceScreen() {
        boolean isTab = DisplayChecker.isDeviceTab(this);
        if (isTab) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }
    }

    private void displayDetails() {
        MovieDetailsModel movieItem = getIntent().getParcelableExtra(MOVIE_SELECTED);
        transitionName =  getIntent().getStringExtra("TEST");
        float rating = movieItem.getVoteAverage();
        tvRating.setText("" + rating);
        addDelayProgress(rating);
        tvOverView.setText("" + movieItem.getOverview());
        tvTitle.setText(movieItem.getTitle());

        setPoster(movieItem.getPosterPath());
    }

    private void addDelayProgress(final float rating) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                roundCornerProgressBar.setProgress(getProgress(rating));
                roundCornerProgressBar.setSecondaryProgress(getProgress(rating));
            }
        }, DELAY);
    }


    private void setPoster(String path) {
        String photoUrl = IMAGE_URL + path;
        Picasso.get()
                .load(photoUrl)
                .fit()
                .placeholder(R.drawable.ic_noimage)
                .into(ivPoster);
    }

    private float getProgress(float rating) {
        float progress = 0;
        try {
            progress = (rating / 10) * 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progress;
    }

    private void initUi() {
        tvRating = findViewById(R.id.tvRating);
        tvTitle = findViewById(R.id.tvTitle);
        tvOverView = findViewById(R.id.tvOverView);
        ivPoster = findViewById(R.id.ivPoster);
        roundCornerProgressBar = findViewById(R.id.roundCornerProgressBar);
    }


}
