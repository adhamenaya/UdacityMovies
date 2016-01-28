package com.adhamenaya.moviesapp.retrofit;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by AENAYA on 7/10/2015.
 */
public interface WebServiceProxy {

    //Base URL for the API
    public static final String ENDPOINT = "http://api.themoviedb.org/3";
    //The sort by parameter
    public static final String SORT_BY_PARAM = "sort_by";
    //API KEY parameter
    public static final String API_KEY_PARAM = "api_key";
    //API KEY
    public static final String API_KEY = "95a631db4bf3378939474e556c2ae9e9";

    //Method to get the list of the movies sorted according to the supplied sortBy parameter
    @GET("/discover/movie?" + API_KEY_PARAM + "=" + API_KEY)
    public MovieData.MovieResult getMovies(@Query(SORT_BY_PARAM) String sortBy);

    @GET("/movie/{id}/reviews?" + API_KEY_PARAM + "=" + API_KEY)
    public MovieReviewData.ReviewResult getMovieReviews(@Path("id") long id);

    @GET("/movie/{id}/videos?" + API_KEY_PARAM + "=" + API_KEY)
    public MovieVideoData.VideoResult getMovieVideos(@Path("id") long id);

    @GET("/movie/{id}/credits?" + API_KEY_PARAM + "=" + API_KEY)
    public MovieCrewData.CreditResult getMovieCredits(@Path("id") long id);

    @GET("/movie/{id}?" + API_KEY_PARAM + "=" + API_KEY)
    public MovieData getMovie(@Path("id") long id);

}
