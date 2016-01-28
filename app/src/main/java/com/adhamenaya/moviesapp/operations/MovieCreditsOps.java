package com.adhamenaya.moviesapp.operations;

import com.adhamenaya.moviesapp.fragments.MovieDetailsFragment;
import com.adhamenaya.moviesapp.retrofit.MovieCrewData;
import com.adhamenaya.moviesapp.retrofit.WebServiceProxy;
import com.adhamenaya.moviesapp.utils.GenericAsyncTask;
import com.adhamenaya.moviesapp.utils.GenericAsyncTaskOps;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.android.MainThreadExecutor;

/**
 * Created by AENAYA on 8/15/2015.
 */
public class MovieCreditsOps implements GenericAsyncTaskOps<Long, Void, MovieCrewData.CreditResult> {

    private GenericAsyncTask<Long, Void, MovieCrewData.CreditResult, MovieCreditsOps> mMovieCreditsOps;
    private WeakReference<MovieDetailsFragment> mMovieDetailsFragmentWeakReference;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    //Retrofit proxy
    private WebServiceProxy mWebServiceProxy;

    public MovieCreditsOps(MovieDetailsFragment movieDetailsFragment) {
        mMovieDetailsFragmentWeakReference = new WeakReference<>((MovieDetailsFragment) movieDetailsFragment);
        //Get the data from the webservice
        mWebServiceProxy = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("MovieCreditLog")).setEndpoint(mWebServiceProxy.ENDPOINT)
                .setExecutors(mExecutor, new MainThreadExecutor()).build().create(WebServiceProxy.class);
    }

    public void executeGetMovieCreditsTask(Long Id) {
        mMovieCreditsOps = new GenericAsyncTask<>(this);
        mMovieCreditsOps.execute(Id);
    }
    @Override
    public void onPreExecute() {

    }
    @Override
    public MovieCrewData.CreditResult doInBackground(Long Id) {

        try {
            // Catch the 400 BAD REQUEST Error
            return mWebServiceProxy.getMovieCredits(Id);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public void onPostExecute(MovieCrewData.CreditResult creditResults, Long param) {
        if (creditResults != null) {
            mMovieDetailsFragmentWeakReference.get().displayMovieCast(creditResults.getCastData());
            mMovieDetailsFragmentWeakReference.get().displayMovieCrew(creditResults.getCrewData());
        }
    }

    public void stopRequest() {
        mExecutor.shutdownNow();
    }
}
