package com.example.movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.api.ApiFactory;
import com.example.movies.databinding.ActivityMovieDetailBinding;
import com.example.movies.pojo.movie.Movie;
import com.example.movies.pojo.trailer.Trailer;
import com.example.movies.pojo.trailer.TrailerResponse;
import com.example.movies.viewmodel.MovieDetailViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    private static final String TAG = "MovieDetailActivity";

    private MovieDetailViewModel movieDetailViewModel;

    private ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(binding.imageViewPoster);


        binding.textViewTitle.setText(
                getString(R.string.title_movie, movie.getName())
        );
        binding.textViewYear.setText(
                getString(R.string.year, String.valueOf(movie.getYear()))
        );
        binding.textViewDescription.setText(
                getString(R.string.description, movie.getDescription())
        );

        movieDetailViewModel.loadTrailer(movie.getId());

        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                Log.d(TAG, trailers.toString());
            }
        });
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}