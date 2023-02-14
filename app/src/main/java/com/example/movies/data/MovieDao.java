package com.example.movies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.pojo.movie.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("select * from favoriteMovies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("select * from favoriteMovies where id = :movieId")
    LiveData<Movie> getFavoriteMovie(int movieId);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("delete from favoriteMovies where id = :movieId")
    Completable removeMovie(int movieId);
}
