package com.adhamenaya.moviesapp.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adhamenaya.moviesapp.R;
import com.adhamenaya.moviesapp.activities.MainActivity;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.adhamenaya.moviesapp.utils.MovieDataAdapter;
import com.adhamenaya.moviesapp.utils.Util;

import java.util.List;

public class MoviesFragment extends Fragment implements View.OnClickListener {


    private OnMovieSelectedListener mOnMovieSelectedListener;

    private OnFragmentAttached mOnFragmentAttached;

    private List<MovieData> mMovies;

    private RecyclerView mMoviesRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private boolean mDisplayMovies = false;

    private Toolbar mToolbar;

    private CoordinatorLayout mCoordinatorLayout;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("MoviesFragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout of the fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Get the grid view.
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI(getView());
    }

    public void setDisplayMovies(List<MovieData> movies) {

        //Set the displayed list of movies
        mMovies = movies;

        // Set the display flag
        mDisplayMovies = true;

    }

    /**
     * This will be called from the activity
     */
    public void displayMovies(List<MovieData> movies, String errorMessage) {

        try {
            if (movies == null) Util.showToast(getActivity(), errorMessage);
            else {

                // Specify an adapter (see also next example)
                mAdapter = new MovieDataAdapter(movies, this);

                //
                mMoviesRecyclerView.setAdapter(mAdapter);

                //Set the displayed list of movies
                mMovies = movies;

            }
        } catch (NullPointerException ex) {
            initUI(getView());
        }
    }

    private void initUI(View view) {

        // Setup the top toolbar
        setupToolbar(view);

        // Setup the floating action button
        setupFloatingActionButton(view);

        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);

        ViewCompat.requestApplyInsets(mCoordinatorLayout);


        // Get the grid view from the layout
        mMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mMoviesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(), 2);

        mMoviesRecyclerView.setLayoutManager(mLayoutManager);

        // Check the display flag
        if (mDisplayMovies) {
            displayMovies(mMovies, "");
            mDisplayMovies = false;
        }
    }

    private void setupToolbar(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    private void setupFloatingActionButton(View view) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openContextMenuFromFragment(v);
            }
        });
    }

    public void displayMovieDetails(MovieData movieData) {
        mOnMovieSelectedListener.onMovieSelected(movieData);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        try {
            mOnMovieSelectedListener = (OnMovieSelectedListener) activity;
            mOnFragmentAttached = (OnFragmentAttached) activity;
            mOnFragmentAttached.onAttach(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMovieSelectedListener");
        }
    }

    public void setToolbarTitle(String title) {
        if (mToolbar != null)
            mToolbar.setTitle(title);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnMovieSelectedListener = null;
    }

    public interface OnMovieSelectedListener {
        public void onMovieSelected(MovieData movieData);
    }

    public interface OnFragmentAttached {
        public void onAttach(Fragment fragment);
    }

    @Override
    public void onClick(View v) {
        int itemPosition = mMoviesRecyclerView.getChildAdapterPosition(v);
        displayMovieDetails(mMovies.get(itemPosition));
        ((MainActivity) getActivity()).onMovieSelected(mMovies.get(itemPosition));
    }
}
