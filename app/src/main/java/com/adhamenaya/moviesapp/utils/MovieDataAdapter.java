package com.adhamenaya.moviesapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adhamenaya.moviesapp.R;
import com.adhamenaya.moviesapp.fragments.MoviesFragment;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AENAYA on 7/10/2015.
 */

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder> {

    private List<MovieData> mMovies = new ArrayList<MovieData>();
    private Context mContext;
    MoviesFragment mMoviesFragment;
    Picasso mPicasso;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Text view for the title
        public TextView titleTV;
        //Text view for the overview
        public TextView overviewTV;
        //Image view for the poster
        public ImageView posterIV;
        // Parent view
        public View view;


        public ViewHolder(final View view) {
            super(view);
            // Get the text view for the title
            titleTV = (TextView) view.findViewById(R.id.titleTV);
            // Get the text view for the overview
            overviewTV = (TextView) view.findViewById(R.id.overviewTV);
            // Get the image view for the poster
            posterIV = (ImageView) view.findViewById(R.id.posterIV);

            this.view = view;

        }
    }

    public MovieDataAdapter(List<MovieData> movies, MoviesFragment moviesFragment) {
        this.mMovies = movies;
        this.mMoviesFragment = moviesFragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        mContext = parent.getContext();

        mPicasso = Picasso.with(mContext);

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_data_row, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);

        // Set on item click listener
        v.setOnClickListener(this.mMoviesFragment);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element

        // Set the title
        holder.titleTV.setText(mMovies.get(position).getTitle());

        // Set the overview
        holder.overviewTV.setText(mMovies.get(position).getOverview());

        // Set the poster image for the movie
        if (mMovies.get(position).getPosterPath() != null
                && !mMovies.get(position).getPosterPath().toLowerCase().equals("null")) {

            // Load the image into the image view
            Util.loadImage(mContext, mMovies.get(position).getPosterPath(), mPicasso, holder.posterIV);

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}
