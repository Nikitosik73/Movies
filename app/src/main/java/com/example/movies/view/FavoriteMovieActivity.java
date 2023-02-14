package com.example.movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movies.viewmodel.FavoriteMovieViewModel;
import com.example.movies.adapter.MoviesAdapter;
import com.example.movies.databinding.ActivityFavoriteMovieBinding;
import com.example.movies.pojo.movie.Movie;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    private ActivityFavoriteMovieBinding binding;

    private FavoriteMovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteMovieBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(FavoriteMovieViewModel.class);

        MoviesAdapter moviesAdapter = new MoviesAdapter();
        binding.recyclerViewFavoriteMovie.setAdapter(moviesAdapter);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        moviesAdapter.setOnClickMovieListener(new MoviesAdapter.onClickMovieListener() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(
                        FavoriteMovieActivity.this,
                        movie
                );
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavoriteMovieActivity.class);
    }
}