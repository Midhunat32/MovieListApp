package com.example.movielistapp.Movies;

import java.util.List;

public interface ApiManager {
    public interface View{
        public void onSuccess(List<MovieItemModel> movieItemModel);
        public void onFailure(String errMsg);
        public void onSuccesItem(MovieItemModel item, int position);

    }
    void fetchDataFromApi(int state);
    void refreshList();
}
