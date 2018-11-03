package com.github.abstractkim.popularmoviesstage1.util.network;

import com.github.abstractkim.popularmoviesstage1.data.MoviesWraper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbWebService {
    @GET("3/movie/popular")
    Observable<MoviesWraper> popularMovies(@Query("page") int page);

    @GET("3/movie/top_rated")
    Observable<MoviesWraper> highestRatedMovies(@Query("page") int page);
}
