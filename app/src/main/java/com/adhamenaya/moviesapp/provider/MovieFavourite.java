package com.adhamenaya.moviesapp.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;

import com.adhamenaya.moviesapp.retrofit.MovieCastData;
import com.adhamenaya.moviesapp.retrofit.MovieCrewData;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.adhamenaya.moviesapp.retrofit.MovieReviewData;
import com.adhamenaya.moviesapp.retrofit.MovieVideoData;
import com.adhamenaya.moviesapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AENAYA on 8/21/2015.
 */
public class MovieFavourite {

    private final static String TAG =
            MovieFavourite.class.getSimpleName();

    private Context mContext;
    private ContentValues contentValues;

    private static final String MOVIE_SELECTION = MovieContract.MovieEntry._ID + " = ? ";
    private static final String MOVIE_SELECTION_SEC = MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID + " = ? ";

    public MovieFavourite(Context context) {
        this.mContext = context;
    }

    private ContentValues makeMovieDataContentValues(MovieData movieData) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry._ID, movieData.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieData.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieData.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieData.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVG, movieData.getVoteAvg());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movieData.getVoteCount());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movieData.getPosterPath());
        contentValues.put(MovieContract.MovieEntry.COLUMN_BUDGET, movieData.getBudget());
        contentValues.put(MovieContract.MovieEntry.COLUMN_REVENUE, movieData.getRevenue());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, movieData.getRuntime());
        contentValues.put(MovieContract.MovieEntry.COLUMN_HOMEPAGE, movieData.getHomepage());

        return contentValues;
    }

    private ContentValues makeMovieCreditsDataContentValues(MovieCrewData movieCrewData, Long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieCreditsEntry._ID, movieCrewData.getId());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_NAME, movieCrewData.getName());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW, movieCrewData.getJob());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_TYPE, 0);
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_POSTER_PATH, movieCrewData.getProfilePath());

        return contentValues;
    }

    private ContentValues makeMovieCreditsDataContentValues(MovieCastData movieCastData, Long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieCreditsEntry._ID, movieCastData.getId());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_NAME, movieCastData.getName());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW, movieCastData.getCharacter());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_TYPE, 1);
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_POSTER_PATH, movieCastData.getProfilePath());

        return contentValues;
    }

    private ContentValues makeMovieVideosDataContentValues(MovieVideoData movieVideoData, Long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieCreditsEntry._ID, movieVideoData.getId());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_NAME, movieVideoData.getName());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW, movieVideoData.getSite());
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieCreditsEntry.COLUMN_TYPE, 2);

        return contentValues;
    }

    private ContentValues makeMovieReviewsDataContentValues(MovieReviewData movieReviewData, Long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieReviewsEntry._ID, movieReviewData.getId());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_AUTHOR, movieReviewData.getAuthor());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_CONTENT, movieReviewData.getContent());
        contentValues.put(MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID, movieId);

        return contentValues;
    }

    public List<MovieData> getAll() {
        List<MovieData> movieDataList = new ArrayList<MovieData>();
        try {
            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null && wdCursor.moveToFirst()) {
                do {
                    movieDataList.add(getMovieDataFromCursor(wdCursor, false));
                } while (wdCursor.moveToNext());

            } else
                // Query was empty or returned null.
                movieDataList = null;

            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();

        } catch (Exception ex) {
            return null;
        }

        return movieDataList;
    }

    private List<MovieReviewData> getAllReviews(Long movieId) {
        List<MovieReviewData> movieReviewDataList = new ArrayList<MovieReviewData>();
        try {
            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_CONTENT_URI,
                            null,
                            MOVIE_SELECTION_SEC,
                            new String[]{String.valueOf(movieId)},
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null && wdCursor.moveToFirst()) {
                do {
                    movieReviewDataList.add(getMovieReviewDataFromCursor(wdCursor));
                } while (wdCursor.moveToNext());
            } else
                // Query was empty or returned null.
                movieReviewDataList = null;
            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();
        } catch (Exception ex) {
            return null;
        }
        return movieReviewDataList;
    }

    private List<MovieCastData> getAllCast(Long movieId) {
        List<MovieCastData> movieCastDataList = new ArrayList<MovieCastData>();
        try {
            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI,
                            null,
                            MOVIE_SELECTION_SEC + " AND " + MovieContract.MovieCreditsEntry.COLUMN_TYPE + " = 1 ",
                            new String[]{String.valueOf(movieId)},
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null
                    && wdCursor.moveToFirst()) {
                do {
                    movieCastDataList.add(getMovieCastDataFromCursor(wdCursor));
                } while (wdCursor.moveToNext());
            } else
                // Query was empty or returned null.
                movieCastDataList = null;
            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();
        } catch (Exception ex) {
            return null;
        }
        return movieCastDataList;
    }

    private List<MovieCrewData> getAllCrew(Long movieId) {
        List<MovieCrewData> movieCrewDataList = new ArrayList<MovieCrewData>();
        try {
            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI,
                            null,
                            MOVIE_SELECTION_SEC + " AND " + MovieContract.MovieCreditsEntry.COLUMN_TYPE + " = 0 ",
                            new String[]{String.valueOf(movieId)},
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null && wdCursor.moveToFirst()) {
                do {
                    movieCrewDataList.add(getMovieCrewDataFromCursor(wdCursor));
                } while (wdCursor.moveToNext());
            } else
                // Query was empty or returned null.
                movieCrewDataList = null;
            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();
        } catch (Exception ex) {
            return null;
        }
        return movieCrewDataList;
    }

    private List<MovieVideoData> getAllVideos(Long movieId) {
        List<MovieVideoData> movieVideoDataList = new ArrayList<MovieVideoData>();
        try {
            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI,
                            null,
                            MOVIE_SELECTION_SEC + " AND " + MovieContract.MovieCreditsEntry.COLUMN_TYPE + " = 2 ",
                            new String[]{String.valueOf(movieId)},
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null && wdCursor.moveToFirst()) {
                do {
                    movieVideoDataList.add(getMovieVideosDataFromCursor(wdCursor));
                } while (wdCursor.moveToNext());
            } else
                // Query was empty or returned null.
                movieVideoDataList = null;
            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();
        } catch (Exception ex) {
            return null;
        }
        return movieVideoDataList;
    }

    public MovieData get(final Long movieId) {
        // Attempt to retrieve the location's data from the content
        // provider.
        MovieData movieData = new MovieData();
        try {

            Cursor wdCursor = mContext.getContentResolver().query
                    (MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                            null,
                            MOVIE_SELECTION,
                            new String[]{String.valueOf(movieId)},
                            null);
            // Check that the cursor isn't null and contains an item.
            if (wdCursor != null && wdCursor.moveToFirst()) {
                movieData = getMovieDataFromCursor(wdCursor, true);
            } else
                // Query was empty or returned null.
                movieData = null;
            if (wdCursor != null && !wdCursor.isClosed())
                wdCursor.close();
        } catch (Exception ex) {
            return null;
        }

        if (movieData != null) {
            movieData.setMovieReviewDataList(getAllReviews(movieId));
            movieData.setMovieCastDataList(getAllCast(movieId));
            movieData.setMovieCrewDataList(getAllCrew(movieId));
            movieData.setMovieVideoDataList(getAllVideos(movieId));
        }
        return movieData;
    }

    private MovieData getMovieDataFromCursor(Cursor data, boolean isFull) {
        if (data == null)
            return null;
        else {
            String releaseDate = "";
            double voteAvg = 0;
            Long budget = 0L;
            Long revenue = 0L;
            String homepage = "";
            int runtime = 0;
            Long voteCount = 0L;

            // Obtain data from the cursor
            Long id = data.getLong(data.getColumnIndex(MovieContract.MovieEntry._ID));
            String title = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
            String overview = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
            String posterPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));

            if (isFull) {
                releaseDate = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                voteAvg = data.getDouble(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVG));
                budget = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BUDGET));
                revenue = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_REVENUE));
                homepage = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_HOMEPAGE));
                runtime = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME));
                voteCount = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BUDGET));
            }
            // Return a movie data object.
            return new MovieData(id, title, overview, releaseDate, voteAvg, posterPath, budget, revenue, homepage, runtime, voteCount);
        }
    }

    private MovieCastData getMovieCastDataFromCursor(Cursor data) {
        if (data == null)
            return null;
        else {
            // Obtain data from the first row.
            String id = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry._ID));
            String name = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_NAME));
            String character = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW));
            String profilePic = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_POSTER_PATH));

            // Return a movie cast object.
            return new MovieCastData(id, name, character, profilePic);

        }
    }

    private MovieCrewData getMovieCrewDataFromCursor(Cursor data) {
        if (data == null)
            return null;
        else {
            // Obtain data from the first row.
            String id = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry._ID));
            String name = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_NAME));
            String job = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW));
            String profilePic = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_POSTER_PATH));

            // Return a crew data object.
            return new MovieCrewData(id, name, job, profilePic);

        }
    }

    private MovieVideoData getMovieVideosDataFromCursor(Cursor data) {
        if (data == null)
            return null;
        else {
            // Obtain data from the first row.
            String id = data.getString(data.getColumnIndex(MovieContract.MovieReviewsEntry._ID));
            String name = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_NAME));
            String site = data.getString(data.getColumnIndex(MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW));

            // Return a movie review object.
            return new MovieVideoData(id, name, site);
        }
    }

    private MovieReviewData getMovieReviewDataFromCursor(Cursor data) {
        if (data == null)
            return null;
        else {
            // Obtain data from the first row.
            String id = data.getString(data.getColumnIndex(MovieContract.MovieReviewsEntry._ID));
            String author = data.getString(data.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_AUTHOR));
            String content = data.getString(data.getColumnIndex(MovieContract.MovieReviewsEntry.COLUMN_CONTENT));

            // Return a movie review object.
            return new MovieReviewData(id, author, content);
        }
    }

    public boolean put(MovieData movieData) {

        if (movieData == null) return false;
        if (get(movieData.getId()) != null) return false;

        try {
            // Index into cvsArray.
            int i = 0;

            // Insert the information about the movie
            Uri in = mContext.getContentResolver().insert(MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                    makeMovieDataContentValues(movieData));


            // Insert the reviews
            if (movieData.getMovieReviewDataList() != null) {
                ContentValues[] cvsReviews = new ContentValues[movieData.getMovieReviewDataList().size()];
                for (MovieReviewData movieReviewData : movieData.getMovieReviewDataList()) {
                    cvsReviews[i++] = makeMovieReviewsDataContentValues(movieReviewData, movieData.getId());
                }
                // Bulk insert the rows into the movie review table.
                mContext.getContentResolver()
                        .bulkInsert(MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_CONTENT_URI, cvsReviews);
            }

            // Insert the Cast rows
            if (movieData.getMovieCastDataList() != null) {
                ContentValues[] cvsCast = new ContentValues[movieData.getMovieCastDataList().size()];
                i = 0;
                for (MovieCastData movieCastData : movieData.getMovieCastDataList()) {
                    cvsCast[i++] = makeMovieCreditsDataContentValues(movieCastData, movieData.getId());
                }
                mContext.getContentResolver()
                        .bulkInsert(MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI, cvsCast);
            }

            // Insert the Crew rows
            if (movieData.getMovieCrewDataList() != null) {
                ContentValues[] cvsCrew = new ContentValues[movieData.getMovieCrewDataList().size()];
                i = 0;
                for (MovieCrewData movieCrewData : movieData.getMovieCrewDataList()) {
                    contentValues = makeMovieCreditsDataContentValues(movieCrewData, movieData.getId());
                    cvsCrew[i++] = contentValues;
                }
                mContext.getContentResolver()
                        .bulkInsert(MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI, cvsCrew);
            }

            // Insert the Videos rows
            if (movieData.getMovieVideoDataList() != null) {
                ContentValues[] cvsCrew = new ContentValues[movieData.getMovieVideoDataList().size()];
                i = 0;
                for (MovieVideoData movieVideoData : movieData.getMovieVideoDataList()) {
                    contentValues = makeMovieVideosDataContentValues(movieVideoData, movieData.getId());
                    cvsCrew[i++] = contentValues;
                }
                mContext.getContentResolver()
                        .bulkInsert(MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI, cvsCrew);
            }
            return get(movieData.getId()) == null ? false : true;
        } catch (SQLiteConstraintException ex) {
            return false;
        }
    }

    public boolean remove(Long movieId) {

        // Delete movie
        mContext.getContentResolver().delete
                (MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                        MOVIE_SELECTION, new String[]{Long.toString(movieId)});

        // Delete reviews
        mContext.getContentResolver().delete
                (MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_CONTENT_URI,
                        MOVIE_SELECTION_SEC, new String[]{Long.toString(movieId)});

        // Delete cast and crew
        mContext.getContentResolver().delete
                (MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI,
                        MOVIE_SELECTION_SEC, new String[]{Long.toString(movieId)});

        return get(movieId) == null ? true : false;

    }

    public int size() {
        // Query the data for all rows of the movie Values table.
        try {
            Cursor cursor =
                    mContext.getContentResolver().query
                            (MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                                    new String[]{MovieContract.MovieEntry._ID},
                                    null,
                                    null,
                                    null);
            return cursor.getCount();

        } catch (Exception ex) {
            return -1;
        }
    }


    public void saveImages(Context context, Picasso picasso, MovieData movieData) {
        // Save the master image
        Util.saveImage(movieData.getPosterPath(), context, picasso);

        // Save the cast images
        if (movieData.getMovieCastDataList() != null)
            for (MovieCastData movieCastData : movieData.getMovieCastDataList()) {
                Util.saveImage(movieCastData.getProfilePath(), context, picasso);
            }

        // Save the crew images
        if (movieData.getMovieCrewDataList() != null)
            for (MovieCrewData movieCrewData : movieData.getMovieCrewDataList()) {
                Util.saveImage(movieCrewData.getProfilePath(), context, picasso);
            }
    }
}
