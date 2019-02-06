package com.example.movielistapp.utility;

import com.example.movielistapp.cloud.responsemodel.fetchmovieid.Result;

public interface CallApiListener {

    void callApi(Result result, int position);
}
