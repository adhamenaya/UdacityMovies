package com.adhamenaya.moviesapp.utils;

/**
 * We created this interface because the class java can't extend to more than one class,
 * So the activity that extends the generic activity can't extend the GenericAsyncTaskOps class.
 *
 * Created by AENAYA on 7/11/2015.
 */
public interface GenericAsyncTaskOps<String, Progress, Result> {

    //Execute in background thread
    public Result doInBackground(String param);

    //Execute in UI thread
    public void onPostExecute(Result result, String param);

    public void onPreExecute();
}
