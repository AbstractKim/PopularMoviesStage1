package com.github.abstractkim.popularmoviesstage1;

import android.content.Context;

import com.github.abstractkim.popularmoviesstage1.data.source.MoviesRepository;
import com.github.abstractkim.popularmoviesstage1.data.source.local.MoviesLocalDataSource;
import com.github.abstractkim.popularmoviesstage1.data.source.remote.MoviesRemoteDataSource;
import com.github.abstractkim.popularmoviesstage1.util.network.NetworkModule;
import com.github.abstractkim.popularmoviesstage1.util.schedulers.BaseSchedulerProvider;
import com.github.abstractkim.popularmoviesstage1.util.schedulers.SchedulerProvider;

import io.reactivex.annotations.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static MoviesRepository provideMoviesRepository(@NonNull Context context){
        checkNotNull(context);
        return MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(NetworkModule.getInstance()),
                MoviesLocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }
}
