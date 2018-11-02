package com.github.abstractkim.popularmoviesstage1.detail;

import com.github.abstractkim.popularmoviesstage1.BasePresenter;
import com.github.abstractkim.popularmoviesstage1.BaseView;
import com.github.abstractkim.popularmoviesstage1.data.Movie;

public class MovieDetailContract {
    interface View extends BaseView<Presenter>{
        void showDetails(Movie movie);
    }

    interface Presenter extends BasePresenter{
        void showDetails(Movie movie);
    }
}
