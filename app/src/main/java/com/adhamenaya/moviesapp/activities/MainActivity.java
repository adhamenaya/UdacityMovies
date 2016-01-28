package com.adhamenaya.moviesapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adhamenaya.moviesapp.R;
import com.adhamenaya.moviesapp.fragments.MovieDetailsFragment;
import com.adhamenaya.moviesapp.fragments.MoviesFragment;
import com.adhamenaya.moviesapp.operations.MovieOps;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.adhamenaya.moviesapp.utils.GenericActivity;
import com.adhamenaya.moviesapp.utils.MovieDataAdapter;
import com.adhamenaya.moviesapp.utils.Util;
import com.adhamenaya.moviesapp.views.SquaredLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This is presentation layer for the MVC, the data and operation are split in another class
 * called 'MovieOps'
 * Created by AENAYA on 7/11/2015.
 */
public class MainActivity extends GenericActivity<MovieOps>
        implements MoviesFragment.OnMovieSelectedListener, MoviesFragment.OnFragmentAttached {

    private GridView mGridView;
    private SquaredLayout mSortBT;
    public final static int BY_POPULARITY = 0;
    public final static int BY_RATING = 1;
    private int mCurrentSortType = -1;
    private String[] mSortByString = {"popularity.desc", "vote_average.desc", "favourite"};
    private String[] mTitles = new String[3];
    private List<MovieData> mMovies;
    private MovieDataAdapter mMovieDataAdapter;
    private int mSelectedMovieIndex = 0;
    private FrameLayout mFrameLayoutRight, mFrameLayoutLeft;
    private LinearLayout mLoader;
    private TextView mNoMoviesTV;

    public MovieDetailsFragment getMovieDetailsFragment() {
        return mMovieDetailsFragment;
    }

    public void setMovieDetailsFragment(MovieDetailsFragment movieDetailsFragment) {
        this.mMovieDetailsFragment = movieDetailsFragment;
    }

    public MoviesFragment getMoviesFragment() {
        return mMoviesFragment;
    }

    public void setMoviesFragment(MoviesFragment moviesFragment) {
        this.mMoviesFragment = moviesFragment;
    }

    private MoviesFragment mMoviesFragment = null;

    private MovieDetailsFragment mMovieDetailsFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Call the super class constructor
        super.onCreate(savedInstanceState);

        // Set the layout for the activity
        setContentView(R.layout.activity_main);

        // Initiate the fragments
        setupFragments();

        // Handel the runtime configuration change
        handleConfiguration(MovieOps.class);

        mTitles[0] = getResources().getString(R.string.toolbar_popularity);
        mTitles[1] = getResources().getString(R.string.toolbar_rating);
        mTitles[2] = getResources().getString(R.string.toolbar_favourite);
    }


    public void setupFragments() {

        // The right fragment that display the details of the selected movie
        mFrameLayoutRight = (FrameLayout) findViewById(R.id.movie_details_fragment_content);
        // The left fragment that displays the grid of the movies
        mFrameLayoutLeft = (FrameLayout) findViewById(R.id.movie_fragment_content);
        mLoader = (LinearLayout) findViewById(R.id.loader);
        mNoMoviesTV = (TextView) findViewById(R.id.noMoviesTV);

        // Check the orientation of the screen
        if (Util.isLand(getApplicationContext()))
            Util.showView(mFrameLayoutRight, true);
        else
            Util.showView(mFrameLayoutRight, false);


        // Try to get the retained fragment for the movies grid
        mMoviesFragment = (MoviesFragment) getRetainedFragmentManager()
                .get().findFragmentByTag(MoviesFragment.class.getSimpleName());

        // Add the fragment to the activity
        if (mMoviesFragment == null) {
            mMoviesFragment = new MoviesFragment();
            getRetainedFragmentManager().get().beginTransaction()
                    .add(R.id.movie_fragment_content, mMoviesFragment, MoviesFragment.class.getSimpleName())
                    .commit();
        }

        // Try to get the retained fragment for the movies grid
        mMovieDetailsFragment = (MovieDetailsFragment) getRetainedFragmentManager()
                .get().findFragmentByTag(MovieDetailsFragment.class.getSimpleName());

        // Add the fragment to the activity
        if (mMovieDetailsFragment == null) {
            mMovieDetailsFragment = new MovieDetailsFragment();
            getRetainedFragmentManager().get().beginTransaction()
                    .add(R.id.movie_details_fragment_content, mMovieDetailsFragment, MovieDetailsFragment.class.getSimpleName())
                    .commit();
        }
    }

    public void showLoader(boolean show) {
        if (mLoader != null)
            if (show) mLoader.setVisibility(View.VISIBLE);
            else mLoader.setVisibility(View.GONE);
    }

    public void showNoMovies(boolean show) {
        if (mNoMoviesTV != null)
            if (show) mNoMoviesTV.setVisibility(View.VISIBLE);
            else mNoMoviesTV.setVisibility(View.GONE);
    }

    /**
     * Open the context menu that contains on the options for downloading
     */
    public void openContextMenuFromFragment(View v) {
        registerForContextMenu(v);
        openContextMenu(v);
        unregisterForContextMenu(v);
    }

    /**
     * Execute the service that gets the list of the movies
     */
    public void getMovies(int sortBy) {
        //Execute the method that gets the movies in the MovieOps object
        getOps().executeGetMoviesTask(mSortByString[sortBy], sortBy, false);
        // Set title
        mMoviesFragment.setToolbarTitle(mTitles[mCurrentSortType]);
        //Set the current sort type
        mCurrentSortType = sortBy;
    }

    /**
     * Set current view
     */
    public void setCurrentView(int index) {
        mCurrentSortType = index;
        mMoviesFragment.setToolbarTitle(mTitles[mCurrentSortType]);
    }

    /**
     * Display the movies after the configuration chang.
     */
    public void displayCachedMovies(List<MovieData> movies) {

        if (movies == null || movies.isEmpty()) Util.showToast(this, "No movies found!");
        else {
            this.mMovies = movies;
            mMoviesFragment.setDisplayMovies(movies);
        }
    }

    /**
     * Display the selected movie after the configuration change.
     */
    public void displayCachedMovieDetails(MovieData movieData) {
        // Add the fragment to the activity
        mMovieDetailsFragment.setDisplayMovies(movieData);
    }

    /**
     * Displays the movies after get their data
     */
    public void displayMovies(List<MovieData> movies, String errorMessage) {
        if (movies == null) Util.showToast(this, errorMessage);
        else {
            this.mMovies = movies;
            mMoviesFragment.displayMovies(movies, errorMessage);
            if (Util.isLand(getApplicationContext()) && movies.size() > 0)
                mMovieDetailsFragment.displayMovieDetails(movies.get(0));
        }
    }

    /**
     * Display data of the selected movie
     */
    public void displayMovieDetails(MovieData movieData) {
        // Add the fragment to the activity
        if (Util.isLand(getApplicationContext())) {
            Util.showView(mFrameLayoutLeft, true);
            Util.showView(mFrameLayoutRight, true);

        } else {
            Util.showView(mFrameLayoutLeft, false);
            Util.showView(mFrameLayoutRight, true);
        }
        mMovieDetailsFragment.displayMovieDetails(movieData);
    }

    /**
     * Add movie to the favourite
     */
    public boolean addFavourite(MovieData movieData) {
        return getOps().addFavourite(movieData);
    }

    /**
     * Remove movie from the favourite
     */
    public boolean removeFavourite(MovieData movieData) {
        return getOps().removeFavourite(movieData);
    }

    public void saveImages(Context context, Picasso picasso, MovieData movieData) {
        getOps().saveImages(context, picasso, movieData);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Add action 1 to the menu
        menu.add(v.getId(), 0, 0, mTitles[0]);

        // Add action 2 to the menu
        menu.add(v.getId(), 1, 1, mTitles[1]);

        // Add action 3 to the menu
        menu.add(v.getId(), 2, 2, mTitles[2]);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get movies sorted by popularity
        if (item.getItemId() == 0) {
            getMovies(0);
            mMoviesFragment.setToolbarTitle(mTitles[0]);
        }
        // Get the movies sorted by rating
        else if (item.getItemId() == 1) {
            getMovies(1);
            mMoviesFragment.setToolbarTitle(mTitles[1]);
        }
        // Get my favourite movies
        else if (item.getItemId() == 2) {
            getMovies(2);
            mMoviesFragment.setToolbarTitle(mTitles[2]);
        }
        return true;
    }

    /**
     * Callback method when the movie selected form the grid.
     */
    @Override
    public void onMovieSelected(MovieData movieData) {
        getOps().executeGetMoviesTask(String.valueOf(movieData.getId()), -1, true);
    }

    @Override
    public void onAttach(Fragment fragment) {
    }

    /**
     * Callback when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        if (!Util.isLand(getApplicationContext())
                && (mFrameLayoutLeft.getVisibility() == View.GONE || mFrameLayoutLeft.getVisibility() == View.INVISIBLE)) {

            Util.showView(mFrameLayoutLeft, true);
            Util.showView(mFrameLayoutRight, false);

            // Refresh after favourite display
            if (mCurrentSortType == 2) getOps().executeGetMoviesTask("favourite", 0, false);
        } else {
            finish();
        }
    }
}
