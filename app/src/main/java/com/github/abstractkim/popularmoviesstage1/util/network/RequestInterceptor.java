package com.github.abstractkim.popularmoviesstage1.util.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.github.abstractkim.popularmoviesstage1.util.network.NetworkModule.TMDB_API_KEY;

public class RequestInterceptor implements Interceptor{

    private static RequestInterceptor INSTANCE = null;
    private RequestInterceptor(){}

    public static RequestInterceptor getInstance() {
        if(INSTANCE == null){
            INSTANCE = new RequestInterceptor();
        }
        return INSTANCE;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", TMDB_API_KEY)
                .build();

        Request request = original.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
