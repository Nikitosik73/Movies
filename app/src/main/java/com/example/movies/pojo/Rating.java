package com.example.movies.pojo;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private String ratingMovie;

    public Rating(String ratingMovie) {
        this.ratingMovie = ratingMovie;
    }

    public String getRatingMovie() {
        return ratingMovie;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingMovie='" + ratingMovie + '\'' +
                '}';
    }
}
