package com.example.movies.pojo.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private double ratingMovie;

    public Rating(double ratingMovie) {
        this.ratingMovie = ratingMovie;
    }

    public double getRatingMovie() {
        return ratingMovie;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingMovie='" + ratingMovie + '\'' +
                '}';
    }
}
