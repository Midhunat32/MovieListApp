package com.example.movielistapp.Movies;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.movielistapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    int TIME_DELAY=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        moveToHome();

    }

    private void moveToHome() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,MoviesActivity.class));
                finish();
            }
        }, TIME_DELAY);
    }


}
