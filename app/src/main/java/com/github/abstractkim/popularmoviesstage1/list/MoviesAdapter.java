package com.github.abstractkim.popularmoviesstage1.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abstractkim.popularmoviesstage1.R;
import com.github.abstractkim.popularmoviesstage1.data.Movie;
import com.github.abstractkim.popularmoviesstage1.util.network.NetworkModule;
import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movies;
    private MoviesContract.View view;

    public MoviesAdapter(List<Movie> movies, MoviesContract.View view) {
        this.movies = movies;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_grid_item, parent,false );

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(viewHolder);
        viewHolder.movie = movies.get(position);
        viewHolder.name.setText(viewHolder.movie.getTitle());

        Picasso.get()
                .load(NetworkModule.getPosterPath(viewHolder.movie.getPosterPath()))
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.user_placeholer_error)
                .into(viewHolder.poster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_poster)
        ImageView poster;
        @BindView(R.id.movie_title_background)
        View titleBackground;
        @BindView(R.id.movie_name)
        TextView name;

        public Movie movie;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }

        @Override
        public void onClick(View view) {
            MoviesAdapter.this.view.onMovieClicked(movie);
        }
    }

}
