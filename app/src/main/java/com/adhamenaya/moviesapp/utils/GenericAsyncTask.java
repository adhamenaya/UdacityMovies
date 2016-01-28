package com.adhamenaya.moviesapp.utils;

import android.os.AsyncTask;

/**
 * Created by AENAYA on 7/11/2015.
 */
public class GenericAsyncTask<Params, Progress, Result, Ops
        extends GenericAsyncTaskOps<Params, Progress, Result>> extends AsyncTask<Params, Progress, Result> {


    private Params mParams;

    private Ops mOps;

    public GenericAsyncTask(Ops ops) {
        this.mOps = ops;
    }

    @Override
    protected void onPreExecute() {
        mOps.onPreExecute();
    }

    /**
     * Background thread
     */
    @Override
    protected Result doInBackground(Params... params) {
        mParams = params[0];
        return mOps.doInBackground(mParams);
    }

    /**
     * UI thread
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        mOps.onPostExecute(result, mParams);
    }
}
