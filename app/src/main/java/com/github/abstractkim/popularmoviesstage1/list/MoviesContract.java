package com.github.abstractkim.popularmoviesstage1.list;

import com.github.abstractkim.popularmoviesstage1.BasePresenter;
import com.github.abstractkim.popularmoviesstage1.BaseView;
import com.github.abstractkim.popularmoviesstage1.data.Movie;

import java.util.List;

public interface MoviesContract {
    interface View extends BaseView<Presenter>{
//        void setLoadingInicator(boolean active);
//        void showLoadingMoviesError();
        void showMovies(List<Movie> movies);
        void onMovieClicked(Movie movie);
        void clearMovies();
        void showMessage(String message);
//        void showMovieDetailUi(String movieID); //parameter could be changed


    }

    interface Presenter extends BasePresenter{
        void firstPage();
        void nextPage();
        void reload(SortType sortType);

    }
}
