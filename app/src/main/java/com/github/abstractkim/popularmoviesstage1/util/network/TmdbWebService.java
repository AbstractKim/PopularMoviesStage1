package com.github.abstractkim.popularmoviesstage1.util.network;

import com.github.abstractkim.popularmoviesstage1.data.MoviesWraper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbWebService {
    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<MoviesWraper> popularMovies(@Query("page") int page);

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Observable<MoviesWraper> highestRatedMovies(@Query("page") int page);
}
