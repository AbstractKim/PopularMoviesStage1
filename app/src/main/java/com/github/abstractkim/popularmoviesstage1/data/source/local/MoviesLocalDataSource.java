package com.github.abstractkim.popularmoviesstage1.data.source.local;


import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.data.source.MoviesDataSource;
import com.github.abstractkim.popularmoviesstage1.list.SortType;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

public class MoviesLocalDataSource implements MoviesDataSource {
    private static MoviesLocalDataSource INSTANCE = null;
    private MoviesLocalDataSource() {}
    public static MoviesLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MoviesLocalDataSource();
        }
        return INSTANCE;
    }
    @Override
    public Flowable<List<Movie>> getMovies(int page, SortType sortType) {
        return null;
    }

    @Override
    public Flowable<Optional<Movie>> getMovie(String movieId) {
        return null;
    }
}
