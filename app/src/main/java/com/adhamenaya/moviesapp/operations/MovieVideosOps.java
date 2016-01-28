package com.adhamenaya.moviesapp.operations;

import com.adhamenaya.moviesapp.fragments.MovieDetailsFragment;
import com.adhamenaya.moviesapp.retrofit.MovieVideoData;
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
public class MovieVideosOps implements GenericAsyncTaskOps<Long, Void, List<MovieVideoData>> {

    private GenericAsyncTask<Long, Void, List<MovieVideoData>, MovieVideosOps> mMovieVideosOps;
    private WeakReference<MovieDetailsFragment> mMovieDetailsFragmentWeakReference;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    //Retrofit proxy
    private WebServiceProxy mWebServiceProxy;

    public MovieVideosOps(MovieDetailsFragment movieDetailsFragment) {
        mMovieDetailsFragmentWeakReference = new WeakReference<>((MovieDetailsFragment) movieDetailsFragment);
        //Get the data from the webservice
        mWebServiceProxy = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("MovieVideoLog")).setEndpoint(mWebServiceProxy.ENDPOINT)
                .setExecutors(mExecutor, new MainThreadExecutor()).build().create(WebServiceProxy.class);
    }

    public void executeGetMovieVideosTask(Long Id) {
        mMovieVideosOps = new GenericAsyncTask<>(this);
        mMovieVideosOps.execute(Id);
    }
    @Override
    public void onPreExecute() {

    }
    @Override
    public List<MovieVideoData> doInBackground(Long Id) {
        try {
            // Catch 400 BAD REQUEST errors
            return mWebServiceProxy.getMovieVideos(Id).getVideoDataList();
        } catch (RuntimeException ex) {
            return null;
        }
    }

    @Override
    public void onPostExecute(List<MovieVideoData> movieVideoData, Long param) {
        if (movieVideoData != null) {
            mMovieDetailsFragmentWeakReference.get().displayMovieVideos(movieVideoData);
        }
    }

    public void stopRequest() {
        mExecutor.shutdownNow();
    }
}
