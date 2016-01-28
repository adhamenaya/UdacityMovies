package com.adhamenaya.moviesapp.operations;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.adhamenaya.moviesapp.activities.MainActivity;
import com.adhamenaya.moviesapp.provider.MovieFavourite;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.adhamenaya.moviesapp.retrofit.WebServiceProxy;
import com.adhamenaya.moviesapp.utils.ConfigurableOps;
import com.adhamenaya.moviesapp.utils.GenericAsyncTask;
import com.adhamenaya.moviesapp.utils.GenericAsyncTaskOps;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * This class will implement Movie related functions
 * Created by AENAYA on 7/11/2015.
 */
public class MovieOps implements ConfigurableOps,
        GenericAsyncTaskOps<String, Void, List<MovieData>> {

    WeakReference<MainActivity> mMainActivityWeakReference;
    //List of movies come from the web service
    private List<MovieData> mMovies;
    private MovieData mOneMovie;
    //Retrofit proxy
    private WebServiceProxy mWebServiceProxy;
    // Ops for movie reviews
    private GenericAsyncTask mReviewAsyncTaskOps;
    private boolean isOne = false;
    private MovieFavourite mMovieFavourite;
    //AsyncTask
    private GenericAsyncTask<String, Void, List<MovieData>, MovieOps> mAsyncTaskOps;

    public MovieOps() {

        mReviewAsyncTaskOps = new GenericAsyncTask<>(this);
        // Create instance for the content provider.
    }

    @Override
    public void onConfiguration(FragmentActivity activity, boolean firstTimeIn) {

        // Get the passed activity
        mMainActivityWeakReference = new WeakReference<>((MainActivity) activity);

        //If is the first time the activity displayed
        if (firstTimeIn) {
            //Get the data from the webservice
            mWebServiceProxy = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("MovieLog")).setEndpoint(mWebServiceProxy.ENDPOINT)
                    .build().create(WebServiceProxy.class);

            mMainActivityWeakReference.get().getMovies(MainActivity.BY_POPULARITY);

        } else {
            //Just update the interface with the same data
            mMainActivityWeakReference.get().displayCachedMovies(mMovies);
            mMainActivityWeakReference.get().displayCachedMovieDetails(mOneMovie);
        }
        mMovieFavourite = new MovieFavourite(getActivity());

    }

    public MainActivity getActivity() {
        return mMainActivityWeakReference.get();
    }

    public void executeGetMoviesTask(String sortBy, int viewIndex, boolean isOne) {

        this.isOne = isOne;

        if (mAsyncTaskOps != null)
            mAsyncTaskOps.cancel(true);

        //Create an instance of the Async task
        mAsyncTaskOps = new GenericAsyncTask<>(this);

        //Execute the background async task
        mAsyncTaskOps.execute(sortBy);

        if (viewIndex != -1)
            getActivity().setCurrentView(viewIndex);
    }

    public boolean addFavourite(MovieData movieData) {
        return mMovieFavourite.put(movieData);
    }

    public boolean removeFavourite(MovieData movieData) {
        return mMovieFavourite.remove(movieData.getId());
    }

    public void saveImages(Context context, Picasso picasso, MovieData movieData) {
        mMovieFavourite.saveImages(context, picasso, movieData);
    }

    @Override
    public void onPreExecute() {
        getActivity().showLoader(true);
        getActivity().showNoMovies(false);
    }

    @Override
    public List<MovieData> doInBackground(String param) {

        // Catch retrofit proxy runtime exception like 400 bad request
        try {
            if (!isOne) {
                if (param.equals("favourite"))
                    // Get from the cache
                    return mMovieFavourite.getAll();
                else
                    try {
                        // Get from the web service
                        return mWebServiceProxy.getMovies(param).getMovies();
                    } catch (Exception ex) {
                        return null;
                    }
            } else {

                Long movieId = Long.parseLong(param);
                List<MovieData> oneMovieList = new ArrayList<MovieData>();

                // First check is in favourite or not
                MovieData movieData = mMovieFavourite.get(movieId);
                if (movieData == null)
                    movieData = mWebServiceProxy.getMovie(movieId);
                else
                    movieData.setFromFavourite(true);

                // Return the movie instance
                if (movieData != null)
                    oneMovieList.add(movieData);

                return oneMovieList;
            }
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public void onPostExecute(List<MovieData> moviesResult, String param) {
        getActivity().showLoader(false);

        if (moviesResult != null && moviesResult.size() > 0) {
            getActivity().showNoMovies(false);
        } else if (mMovies == null || mMovies.size() == 0) {
            getActivity().showNoMovies(true);
        }

        // Rebind the list any way to remove any non-exist previous movies
        List<MovieData> movieDataListTemp = new ArrayList<MovieData>();
        if (moviesResult == null)
            moviesResult = movieDataListTemp;

        if (!isOne) {
            mMovies = moviesResult;
            // Display the data in the activity
            mMainActivityWeakReference.get().displayMovies(mMovies, "No movies found!");
        } else {
            if (moviesResult.size() > 0) {
                mOneMovie = moviesResult.get(0);
                mMainActivityWeakReference.get().displayMovieDetails(mOneMovie);
            }
        }
    }

    public void getMovieReviews(String movieId) {
        mReviewAsyncTaskOps.execute(movieId);
    }
}
