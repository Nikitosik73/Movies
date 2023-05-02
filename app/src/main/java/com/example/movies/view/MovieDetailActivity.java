package com.example.movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.adapter.ReviewAdapter;
import com.example.movies.adapter.TrailerAdapter;
import com.example.movies.data.MovieDatabase;
import com.example.movies.databinding.ActivityMovieDetailBinding;
import com.example.movies.pojo.movie.Movie;
import com.example.movies.pojo.review.Review;
import com.example.movies.pojo.trailer.Trailer;
import com.example.movies.viewmodel.MovieDetailViewModel;

import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    private static final String TAG = "MovieDetailActivity";

    private MovieDetailViewModel movieDetailViewModel;

    private ActivityMovieDetailBinding binding;

    private TrailerAdapter trailerAdapter = new TrailerAdapter();

    private ReviewAdapter reviewAdapter = new ReviewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        binding.recyclerViewTrailer.setAdapter(trailerAdapter);
        binding.recyclerViewReview.setAdapter(reviewAdapter);

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
                trailerAdapter.setTrailers(trailers);
            }
        });

        trailerAdapter.setOnClickTrailerListener(new TrailerAdapter.onClickTrailerListener() {
            @Override
            public void onClickTrailer(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

        movieDetailViewModel.loadReview(movie.getId());

        Drawable starOff = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_off
        );
        Drawable starOn = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_on
        );
        movieDetailViewModel.getFavoriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDB) {
                if (movieFromDB == null){
                    binding.imageViewStar.setImageDrawable(starOff);
                    binding.imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieDetailViewModel.insertMovie(movie);
                        }
                    });
                } else {
                    binding.imageViewStar.setImageDrawable(starOn);
                    binding.imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieDetailViewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}