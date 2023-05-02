package com.example.movies.pojo.trailer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrailerList implements Serializable {
    @SerializedName("trailers")
    private List<Trailer> trailers;

    public TrailerList(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "trailers=" + trailers +
                '}';
    }
}
