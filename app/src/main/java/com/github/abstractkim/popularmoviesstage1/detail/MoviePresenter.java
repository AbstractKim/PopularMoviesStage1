package com.github.abstractkim.popularmoviesstage1.detail;

import android.util.Log;

import com.github.abstractkim.popularmoviesstage1.data.Movie;

import io.reactivex.annotations.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviePresenter implements MovieDetailContract.Presenter {
    public static final String TAG = MoviePresenter.class.getSimpleName();

    MovieDetailContract.View view;

    MoviePresenter(@NonNull MovieDetailContract.View view){
        this.view = checkNotNull(view);
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void showDetails(Movie movie) {
        Log.d(TAG, "showDetails(movie)" + movie.toString());
        view.showDetails(movie);
    }
}
