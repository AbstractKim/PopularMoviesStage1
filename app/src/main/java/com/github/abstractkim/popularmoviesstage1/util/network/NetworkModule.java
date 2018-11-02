package com.github.abstractkim.popularmoviesstage1.util.network;

import java.util.concurrent.TimeUnit;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.github.abstractkim.popularmoviesstage1.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NetworkModule {
    public static final int CONNECT_TIMEOUT_IN_MS = 30000;
    public static final String TMDB_BASE_URL = "http://api.themoviedb.org/";
    public static final String TMDB_API_KEY ="";
    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342";

    private static TmdbWebService tmdbWebService = null;
    private static NetworkModule INSTANCE = null;
    private NetworkModule(){}
    public static NetworkModule getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NetworkModule();
        }
        return INSTANCE;
    }

    RequestInterceptor requestInterceptor(){
        return RequestInterceptor.getInstance();
    }

    OkHttpClient provideOkHttpClient(RequestInterceptor requestInterceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(requestInterceptor);

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    TmdbWebService tmdbWebService(Retrofit retrofit){
        return retrofit.create(TmdbWebService.class);
    }


    //applying dagger later...
    public TmdbWebService buildTmdbWebService(){
        if(tmdbWebService == null){
            RequestInterceptor requestInterceptor = requestInterceptor();
            OkHttpClient okHttpClient = provideOkHttpClient(requestInterceptor);
            Retrofit retrofit = retrofit(okHttpClient);
            tmdbWebService = tmdbWebService(retrofit);
        }
        return tmdbWebService;
    }

    public static String getPosterPath(String posterPath) {
        return BASE_POSTER_PATH + posterPath;
    }

}
