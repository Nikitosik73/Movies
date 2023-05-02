package com.example.movies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.MovieDatabase;
import com.example.movies.pojo.movie.Movie;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private final MovieDatabase movieDatabase;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieDatabase = MovieDatabase.getInstance(getApplication());
    }

    public LiveData<List<Movie>> getMovies(){
        return movieDatabase.movieDao().getAllFavoriteMovies();
    }
}
