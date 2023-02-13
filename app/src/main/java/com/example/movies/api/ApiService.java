package com.example.movies.api;

import com.example.movies.pojo.movie.MovieResponse;
import com.example.movies.pojo.review.ReviewResponse;
import com.example.movies.pojo.trailer.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=MPKAE83-MD4MWH8-P9Z9QS4-NH75ZZ0&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=20")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=MPKAE83-MD4MWH8-P9Z9QS4-NH75ZZ0&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=MPKAE83-MD4MWH8-P9Z9QS4-NH75ZZ0&field=movieId")
    Single<ReviewResponse> loadReviews(@Query("search") int movieId);
}
