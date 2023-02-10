package com.example.movies.api;

import com.example.movies.pojo.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=RVGRHT3-KS8MS22-MNRSHR5-ZBK39EJ&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=5")
    Single<MovieResponse> loadMovies(@Query("page") int page);
}
