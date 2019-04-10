package com.example.movielistapp.Movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.MovieDetailsModel;

public class MovieItemModel implements Parcelable {
    String id;
    MovieDetailsModel data;

    protected MovieItemModel(Parcel in) {
        id = in.readString();
    }

    public static final Creator<MovieItemModel> CREATOR = new Creator<MovieItemModel>() {
        @Override
        public MovieItemModel createFromParcel(Parcel in) {
            return new MovieItemModel(in);
        }

        @Override
        public MovieItemModel[] newArray(int size) {
            return new MovieItemModel[size];
        }
    };

    public MovieItemModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieDetailsModel getData() {
        return data;
    }

    public void setData(MovieDetailsModel data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
