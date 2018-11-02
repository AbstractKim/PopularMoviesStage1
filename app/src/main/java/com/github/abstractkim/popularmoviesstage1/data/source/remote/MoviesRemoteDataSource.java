package com.github.abstractkim.popularmoviesstage1.data.source.remote;

import android.util.Log;

import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.data.MoviesWraper;
import com.github.abstractkim.popularmoviesstage1.data.source.MoviesDataSource;
import com.github.abstractkim.popularmoviesstage1.data.source.MoviesRepository;
import com.github.abstractkim.popularmoviesstage1.list.SortType;
import com.github.abstractkim.popularmoviesstage1.util.network.NetworkModule;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesRemoteDataSource implements MoviesDataSource {
    public static final String TAG = MoviesRemoteDataSource.class.getSimpleName();

    private static MoviesRemoteDataSource INSTANCE;
    private NetworkModule networkModule;

    private MoviesRemoteDataSource(@NonNull NetworkModule networkModule) {
        this.networkModule = checkNotNull(networkModule, "checkNotNull cannot be null");
    }

    public static MoviesRemoteDataSource getInstance(NetworkModule networkModule) {
        if(INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource(networkModule);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Movie>> getMovies(int page, SortType sortType) {
        //request retorofit service
        Log.d(TAG, "getMovies() - page: " + page);
        Flowable<List<Movie>> flowable;
        if(sortType == SortType.MOST_POPULAR){
            flowable = networkModule.buildTmdbWebService()
                    .popularMovies(page)
                    .map(MoviesWraper::getMovies)
                    .toFlowable(BackpressureStrategy.BUFFER);
        }else {
          flowable = networkModule.buildTmdbWebService()
                    .highestRatedMovies(page)
                    .map(MoviesWraper::getMovies)
                    .toFlowable(BackpressureStrategy.BUFFER);
        }
        return flowable;
    }

    @Override
    public Flowable<Optional<Movie>> getMovie(String movieId) {
        //request retorofit service
        return null;
    }
}
