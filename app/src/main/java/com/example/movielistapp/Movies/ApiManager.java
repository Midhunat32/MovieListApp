package com.example.movielistapp.Movies;

import java.util.List;

public interface ApiManager {
    public interface View{
        public void onSuccess(List<DummyModel> dummyModel);
        public void onFailure(String errMsg);
        public void onSuccesItem(DummyModel item,int position);

    }
    void fetchDataFromApi();
    void refreshList();
}
