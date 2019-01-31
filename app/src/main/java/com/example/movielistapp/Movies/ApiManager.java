package com.example.movielistapp.Movies;

import java.util.List;

public interface ApiManager {
    public interface View{
        public void onSuccess(List<DummyModel> dummyModel);
        public void onFailure(String errMsg);
    }
    void fetchDataFromApi(ApiManager.View managerView,List<DummyModel> listDummyModel);
}
