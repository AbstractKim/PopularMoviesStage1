package com.github.abstractkim.popularmoviesstage1.data.source;


import android.util.Log;

import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.list.SortType;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesRepository implements MoviesDataSource {
    public static final String TAG = MoviesRepository.class.getSimpleName();

    @Nullable
    private static MoviesRepository INSTANCE = null;

    @NonNull
    private final MoviesDataSource moviesRemoteDataSource;

    @NonNull
    private final MoviesDataSource moviesLocalDataSource;


    private MoviesRepository(@NonNull MoviesDataSource moviesRemoteDataSource,
                            @NonNull MoviesDataSource moviesLocalDataSource) {
        this.moviesRemoteDataSource = checkNotNull(moviesRemoteDataSource);
        this.moviesLocalDataSource = checkNotNull(moviesLocalDataSource);
    }

    public static MoviesRepository getInstance(@NonNull MoviesDataSource moviesRemoteDataSource,
                                              @NonNull MoviesDataSource moviesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository(moviesRemoteDataSource, moviesLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Movie>> getMovies(int page, SortType sortType) {
        //cache and local and concat will implement later
        Log.d(TAG, "onMovie()- page:" + page);
        return moviesRemoteDataSource.getMovies(page, sortType);
    }

    @Override
    public Flowable<Optional<Movie>> getMovie(String movieId) {
        return null;
    }
}
