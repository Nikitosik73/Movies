package com.example.movies.pojo.trailer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrailerResponse implements Serializable {
    @SerializedName("videos")
    private TrailerList trailerList;

    public TrailerResponse(TrailerList trailerList) {
        this.trailerList = trailerList;
    }

    public TrailerList getTrailerList() {
        return trailerList;
    }

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "videos=" + trailerList +
                '}';
    }
}
