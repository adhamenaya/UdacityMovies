package com.adhamenaya.moviesapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by AENAYA on 8/21/2015.
 */
public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "udacity_movie_db";
    private static int DATABASE_VERSION = 3;

    /**
     * SQL statement used to create the Movie table.
     */
    private static final String CREATE_TABLE_MOVIE =
            "CREATE TABLE "
                    + MovieContract.MovieEntry.MOVIES_TABLE_NAME
                    + "("
                    + MovieContract.MovieEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + MovieContract.MovieEntry.COLUMN_TITLE
                    + " TEXT, "
                    + MovieContract.MovieEntry.COLUMN_OVERVIEW
                    + " TEXT, "
                    + MovieContract.MovieEntry.COLUMN_RELEASE_DATE
                    + " TEXT, "
                    + MovieContract.MovieEntry.COLUMN_VOTE_AVG
                    + " REAL, "
                    + MovieContract.MovieEntry.COLUMN_VOTE_COUNT
                    + " INTEGER, "
                    + MovieContract.MovieEntry.COLUMN_POSTER_PATH
                    + " TEXT, "
                    + MovieContract.MovieEntry.COLUMN_BUDGET
                    + " INTEGER, "
                    + MovieContract.MovieEntry.COLUMN_REVENUE
                    + " INTEGER, "
                    + MovieContract.MovieEntry.COLUMN_RUNTIME
                    + " INTEGER, "
                    + MovieContract.MovieEntry.COLUMN_HOMEPAGE
                    + " TEXT)";


    private static final String CREATE_TABLE_MOVIE_REVIEWS =
            "CREATE TABLE "
                    + MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME
                    + "("
                    + MovieContract.MovieReviewsEntry._ID
                    + " TEXT PRIMARY KEY, "
                    + MovieContract.MovieReviewsEntry.COLUMN_AUTHOR
                    + " TEXT, "
                    + MovieContract.MovieReviewsEntry.COLUMN_CONTENT
                    + " TEXT, "
                    + MovieContract.MovieReviewsEntry.COLUMN_MOVIE_ID
                    + " INTEGER)";

    private static final String CREATE_TABLE_MOVIE_CREDITS =
            "CREATE TABLE "
                    + MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME
                    + "("
                    + MovieContract.MovieCreditsEntry._ID
                    + " TEXT PRIMARY KEY, "
                    + MovieContract.MovieCreditsEntry.COLUMN_NAME
                    + " TEXT, "
                    + MovieContract.MovieCreditsEntry.COLUMN_OVERVIEW
                    + " TEXT, "
                    + MovieContract.MovieCreditsEntry.COLUMN_POSTER_PATH
                    + " TEXT, "
                    + MovieContract.MovieCreditsEntry.COLUMN_MOVIE_ID
                    + " INTEGER, "
                    + MovieContract.MovieCreditsEntry.COLUMN_TYPE
                    + " INTEGER)";

    public MovieDatabaseHelper(Context context) {
        super(context, context.getCacheDir() + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables.
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_MOVIE_CREDITS);
        db.execSQL(CREATE_TABLE_MOVIE_REVIEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete the existing tables.
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.MOVIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME);

        // Create the new tables.
        onCreate(db);
    }
}
