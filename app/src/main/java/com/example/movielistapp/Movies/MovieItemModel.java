package com.example.movielistapp.Movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.movielistapp.cloud.responsemodel.fetchmoviedetails.DataItemModel;

public class MovieItemModel implements Parcelable {
    String id;
    DataItemModel data;

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

    public DataItemModel getData() {
        return data;
    }

    public void setData(DataItemModel data) {
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
