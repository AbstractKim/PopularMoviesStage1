package com.github.abstractkim.popularmoviesstage1.data.source;

import com.github.abstractkim.popularmoviesstage1.data.Movie;

import java.util.List;

import com.github.abstractkim.popularmoviesstage1.list.SortType;
import com.google.common.base.Optional;


import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public interface MoviesDataSource {
    Flowable<List<Movie>> getMovies(int page, SortType sortType);
    Flowable<Optional<Movie>> getMovie(@NonNull String movieId);
}
