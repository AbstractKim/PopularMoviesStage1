package com.github.abstractkim.popularmoviesstage1.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.abstractkim.popularmoviesstage1.Constants;
import com.github.abstractkim.popularmoviesstage1.Injection;
import com.github.abstractkim.popularmoviesstage1.R;
import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.detail.MovieDetailActivty;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements MoviesContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MoviesContract.Presenter presenter;

    @BindView(R.id.movies_listing)
    RecyclerView moviesListing;

    private RecyclerView.Adapter adapter;
    private List<Movie> movies = new ArrayList<>(20);
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MoviesPresenter(
                Injection.provideMoviesRepository(getApplicationContext()),
                this,
                Injection.provideSchedulerProvider());

        unbinder = ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, calculateNoColumns(getApplicationContext()));
        moviesListing.setLayoutManager(layoutManager);
        adapter = new MoviesAdapter(movies, this);
        moviesListing.setAdapter(adapter);

        moviesListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    presenter.nextPage();
                }
            }
        });
    }

    public static int calculateNoColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_most_popular:
                //Toast.makeText(this, "most popuar", Toast.LENGTH_LONG).show();
                presenter.reload(SortType.MOST_POPULAR);
                break;
            case R.id.menu_highest_rated:
                //Toast.makeText(this, "highest rate", Toast.LENGTH_LONG).show();
                presenter.reload(SortType.HIGST_RATED);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(@NonNull MoviesContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        Log.d(TAG, "showMovies() - size:"  + movies.size());
        //this.movies.clear();
        this.movies.addAll(movies);
        moviesListing.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearMovies() {
        this.movies.clear();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        //launch detail veiw
        //Toast.makeText(this, movie.getTitle(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MovieDetailActivty.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.MOVIE, movie);
        intent.putExtras(extras);
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        checkNotNull(presenter).subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        checkNotNull(presenter).unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

