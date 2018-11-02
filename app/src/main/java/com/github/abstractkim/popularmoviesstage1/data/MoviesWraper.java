package com.github.abstractkim.popularmoviesstage1.data;

import com.squareup.moshi.Json;

import java.util.List;

public class MoviesWraper {
    @Json(name = "results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MoviesWraper{" +
                "movies=" + movies +
                '}';
    }
}
