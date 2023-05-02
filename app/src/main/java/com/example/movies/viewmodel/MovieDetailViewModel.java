package com.example.movies.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.api.ApiFactory;
import com.example.movies.data.MovieDatabase;
import com.example.movies.pojo.movie.Movie;
import com.example.movies.pojo.review.Review;
import com.example.movies.pojo.review.ReviewResponse;
import com.example.movies.pojo.trailer.Trailer;
import com.example.movies.pojo.trailer.TrailerResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private MovieDatabase movieDatabase;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDatabase = MovieDatabase.getInstance(getApplication());
    }

    private static final String TAG = "MovieDetailViewModel";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public LiveData<Movie> getFavoriteMovie(int movieId) {
        return movieDatabase.movieDao().getFavoriteMovie(movieId);
    }

    public void loadTrailer(int id){

        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailerList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, trailers.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReview(int movieId){

        Disposable disposable = ApiFactory.apiService.loadReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviewsList) throws Throwable {
                        reviews.setValue(reviewsList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void insertMovie(Movie movie){
        Disposable disposable = movieDatabase.movieDao().insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d(TAG, "AddMovie: " + movie.getId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieId){
        Disposable disposable = movieDatabase.movieDao().removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d(TAG, "RemoveMovie: " + movieId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
