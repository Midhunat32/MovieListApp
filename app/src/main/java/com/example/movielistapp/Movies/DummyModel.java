package com.example.movielistapp.Movies;

import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;

public class DummyModel {
    String id;
    DataItemModel data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataItemModel getData() {
        return data;
    }

    public void setData(DataItemModel data) {
        this.data = data;
    }
}
