package com.example.movielistapp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.movielistapp.MovieAppConstants;
import com.example.movielistapp.R;
import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;
import com.example.movielistapp.ui.activity.BaseActivity;
import com.example.movielistapp.utility.DisplayChecker;
import com.squareup.picasso.Picasso;

import static com.example.movielistapp.MovieAppConstants.IMAGE_URL;
import static com.example.movielistapp.MovieAppConstants.MOVIE_SELECTED;

public class MovieDetailsActivity extends BaseActivity {
    private TextView tvRating, tvTitle, tvOverView;
    private ImageView ivPoster;
    private int DELAY = 1000;
    RoundCornerProgressBar roundCornerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initUi();
        displayDetails();
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
        DataItemModel movieItem = getIntent().getParcelableExtra(MOVIE_SELECTED);
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
