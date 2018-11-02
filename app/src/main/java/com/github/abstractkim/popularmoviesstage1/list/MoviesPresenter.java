package com.github.abstractkim.popularmoviesstage1.list;

import android.util.Log;

import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.data.source.MoviesRepository;
import com.github.abstractkim.popularmoviesstage1.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoviesPresenter implements MoviesContract.Presenter {
    public static final String TAG = MoviesPresenter.class.getSimpleName();
    private int curPage;
    SortType sortType = SortType.MOST_POPULAR;

    @NonNull
    private final MoviesRepository moviesRepository;

    @NonNull
    private final MoviesContract.View moviewView;

    @NonNull
    private final BaseSchedulerProvider baseSchedulerProvider;

    @NonNull
    private CompositeDisposable compositeDisposable;

    public MoviesPresenter(@NonNull MoviesRepository moviesRepository,
                           @NonNull MoviesContract.View moviewView,
                           @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        this.moviesRepository = checkNotNull(moviesRepository
                ,"moviesRepository cannot be null" );
        this.moviewView = checkNotNull(moviewView
                ,"moviewView cannot be null" );
        this.baseSchedulerProvider = checkNotNull(baseSchedulerProvider
                ,"baseSchedulerProvider cannot be null" );
        compositeDisposable =  new CompositeDisposable();
        moviewView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Log.d(TAG, "subscribe()");
        firstPage();
    }

    @Override
    public void firstPage() {
        Log.d(TAG, "firstPage()");
        curPage = 1;
        getMovies(curPage, sortType);
    }

    @Override
    public void nextPage() {
        Log.d(TAG, "nextPage()");
        curPage++;
        getMovies(curPage, sortType);
    }

    @Override
    public void reload(SortType sortType) {
        if(this.sortType != sortType){
            this.sortType = sortType;
            moviewView.clearMovies();
            firstPage();
        }else{
            moviewView.showMessage("It is already sorted by " + sortType.toString());
        }
    }

    private void getMovies(int page, SortType sortType) {
        Log.d(TAG, "openMovies() - page:" + page);
        //load movies
        Disposable subscribe =
                moviesRepository.getMovies(page, sortType)
                .subscribeOn(baseSchedulerProvider.io())
                .observeOn(baseSchedulerProvider.ui())
                .subscribe(this::showMovies, this::showError);
        compositeDisposable.add(subscribe);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void showMovies(@NonNull List<Movie> movies) {
        Log.d(TAG, "showMovies()\n"  + movies.toString());
        moviewView.showMovies(movies);
    }
    private void showError(@NonNull Throwable throwable) {
        Log.d(TAG, throwable.toString());
    }
}
