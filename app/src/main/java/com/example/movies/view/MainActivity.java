package com.example.movies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movies.adapter.MoviesAdapter;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.pojo.Movie;
import com.example.movies.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MoviesAdapter moviesAdapter = new MoviesAdapter();
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerViewMovie.setAdapter(moviesAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {

                if (loading) {
                    binding.progressBarLoading.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBarLoading.setVisibility(View.GONE);
                }
            }
        });

        moviesAdapter.setOnReachEndListener(new MoviesAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                mainViewModel.loadMovies();
            }
        });

        moviesAdapter.setOnClickMovieListener(new MoviesAdapter.onClickMovieListener() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(
                        MainActivity.this,
                        movie
                );
                startActivity(intent);
            }
        });
    }
}