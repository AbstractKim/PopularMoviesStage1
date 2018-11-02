package com.github.abstractkim.popularmoviesstage1.detail;

import android.annotation.SuppressLint;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abstractkim.popularmoviesstage1.BasePresenter;
import com.github.abstractkim.popularmoviesstage1.Constants;
import com.github.abstractkim.popularmoviesstage1.R;
import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.list.MoviesContract;
import com.github.abstractkim.popularmoviesstage1.util.network.NetworkModule;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MovieDetailActivty extends AppCompatActivity implements MovieDetailContract.View {


    private static final String TAG = MovieDetailActivty.class.getSimpleName();
    private MovieDetailContract.Presenter presenter;

    @BindView(R.id.movie_poster)
    ImageView poster;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.movie_name)
    TextView title;
    @BindView(R.id.movie_year)
    TextView releaseDate;
    @BindView(R.id.movie_rating)
    TextView rating;
    @BindView(R.id.movie_description)
    TextView plot_synopsis;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_activty);

        unbinder = ButterKnife.bind(this);
        presenter = new MoviePresenter(this);

        Bundle extras = getIntent().getExtras();
        if( extras != null && extras.containsKey(Constants.MOVIE)){
            Movie movie = extras.getParcelable(Constants.MOVIE);
            if(movie != null){
                presenter.showDetails(movie);
            }
        }
    }

    @Override
    public void setPresenter(@NonNull MovieDetailContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        unbinder.unbind();
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void showDetails(Movie movie) {
        Log.d(TAG, "showDetails(movie)" );
        Picasso.get()
                .load(NetworkModule.getPosterPath(movie.getBackdropPath()))
                .fit()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.user_placeholer_error)
                .into(poster);
        title.setText(movie.getTitle());
        releaseDate.setText(String.format(getString(R.string.release_date) + " : " + movie.getReleaseDate()));
        rating.setText(String.format(getString(R.string.rating) + " : " + String.valueOf(movie.getVoteAverage())));
        plot_synopsis.setText(movie.getOverview());

    }
}
