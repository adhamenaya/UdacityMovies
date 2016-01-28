package com.adhamenaya.moviesapp.operations;

import com.adhamenaya.moviesapp.fragments.MovieDetailsFragment;
import com.adhamenaya.moviesapp.retrofit.MovieReviewData;
import com.adhamenaya.moviesapp.retrofit.WebServiceProxy;
import com.adhamenaya.moviesapp.utils.GenericAsyncTask;
import com.adhamenaya.moviesapp.utils.GenericAsyncTaskOps;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.android.MainThreadExecutor;

/**
 * Created by AENAYA on 8/15/2015.
 */
public class MovieReviewsOps implements GenericAsyncTaskOps<Long, Void, List<MovieReviewData>> {

    private GenericAsyncTask<Long, Void, List<MovieReviewData>, MovieReviewsOps> mMovieReviewsOps;
    private WeakReference<MovieDetailsFragment> mMovieDetailsFragmentWeakReference;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    //Retrofit proxy
    private WebServiceProxy mWebServiceProxy;

    public MovieReviewsOps(MovieDetailsFragment movieDetailsFragment) {
        mMovieDetailsFragmentWeakReference = new WeakReference<>((MovieDetailsFragment) movieDetailsFragment);
        //Get the data from the webservice
        mWebServiceProxy = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("MovieReviewLog")).setEndpoint(mWebServiceProxy.ENDPOINT)
                .setExecutors(mExecutor, new MainThreadExecutor()).build().create(WebServiceProxy.class);
    }

    public void executeGetMovieReviewsTask(Long Id) {
        mMovieReviewsOps = new GenericAsyncTask<>(this);
        mMovieReviewsOps.execute(Id);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public List<MovieReviewData> doInBackground(Long Id) {
        try {
            // Catch the 400 BAD REQUEST Error
            return mWebServiceProxy.getMovieReviews(Id).getReviewDataList();
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public void onPostExecute(List<MovieReviewData> movieReviewData, Long param) {
        if (movieReviewData != null) {
            mMovieDetailsFragmentWeakReference.get().displayMovieReviews(movieReviewData);
        }
    }

    public void stopRequest() {
        mExecutor.shutdownNow();
    }
}
