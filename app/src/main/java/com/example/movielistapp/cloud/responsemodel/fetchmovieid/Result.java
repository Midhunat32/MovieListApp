package com.example.movielistapp.cloud.responsemodel.fetchmovieid;

import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    private MovieDetailsModel movieDetailsModel;

    public MovieDetailsModel getMovieDetailsModel() {
        return movieDetailsModel;
    }

    public void setMovieDetailsModel(MovieDetailsModel movieDetailsModel) {
        this.movieDetailsModel = movieDetailsModel;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
}
