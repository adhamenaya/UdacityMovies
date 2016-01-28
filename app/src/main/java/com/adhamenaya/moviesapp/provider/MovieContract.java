package com.adhamenaya.moviesapp.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AENAYA on 8/21/2015.
 */

public final class MovieContract {

    // Authority identifier for the movies content
    public static final String AUTHORITY = "com.adhamenaya.movies";

    // URL to communicate with the movies content provider
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * Constant for a directory MIME type.
     */
    private static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/";

    /**
     * Constant for a single item MIME type.
     */
    private static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/";

    public static final String ACCESS_ALL_MOVIES_PATH = "access_all_movies";

    public static final Uri ACCESS_ALL_MOVIES_URI =
            BASE_URI.buildUpon().appendPath(ACCESS_ALL_MOVIES_PATH).build();

    public static final String ACCESS_ALL_MOVIES =
            MIME_TYPE_DIR + AUTHORITY + "/" + ACCESS_ALL_MOVIES_PATH;

    /**
     * Inner class defining the contents of the movie table.
     */
    public static final class MovieEntry
            implements BaseColumns {

        public static String MOVIES_TABLE_NAME = "movies";

        /**
         * Unique URI for the movie table.
         */
        public static final Uri MOVIE_CONTENT_URI =
                BASE_URI.buildUpon()
                        .appendPath(MOVIES_TABLE_NAME)
                        .build();

        /**
         * MIME type for multiple movie rows.
         */
        public static final String MOVIE_ITEMS =
                MIME_TYPE_DIR
                        + AUTHORITY
                        + "/"
                        + MOVIES_TABLE_NAME;

        /**
         * MIME type for a single movie row
         */
        public static final String MOVIE_ITEM =
                MIME_TYPE_ITEM
                        + AUTHORITY
                        + "/"
                        + MOVIES_TABLE_NAME;

        /*
         * Movie Table's Columns.
         */

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVG = "vote_avg";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BUDGET = "budget";
        public static final String COLUMN_REVENUE = "revenue";
        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_VOTE_COUNT = "vote_count";


        /**
         * Return a URI that points to the row containing the given
         * ID.
         */
        public static Uri buildRowAccessUri(Long id) {
            return ContentUris.withAppendedId(MOVIE_CONTENT_URI, id);
        }
    }

    /**
     * Inner class defining the contents of the movie reviews table.
     */
    public static final class MovieReviewsEntry
            implements BaseColumns {
        /**
         * Movie reviews Table name.
         */
        public static String MOVIE_REVIEWS_TABLE_NAME = "movie_reviews";

        /**
         * Unique URI for the Movie reviews table.
         */
        public static final Uri MOVIE_REVIEWS_CONTENT_URI =
                BASE_URI.buildUpon()
                        .appendPath(MOVIE_REVIEWS_TABLE_NAME)
                        .build();

        /**
         * MIME type for multiple Movie Reviews rows
         */
        public static final String MOVIE_REVIEWS_ITEMS =
                MIME_TYPE_DIR
                        + AUTHORITY
                        + "/"
                        + MOVIE_REVIEWS_TABLE_NAME;

        /**
         * MIME type for a single Movie Reviews row.
         */
        public static final String MOVIE_REVIEWS_ITEM =
                MIME_TYPE_ITEM
                        + AUTHORITY
                        + "/"
                        + MOVIE_REVIEWS_TABLE_NAME;

        /*
         * Movie Reviews Table's Columns
         */
        public static final String COLUMN_AUTHOR = "main";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MOVIE_ID = "movie_id";


        /**
         * Return a URI that points to the row containing the given ID.
         */
        public static Uri buildRowAccessUri(Long id) {
            return ContentUris.withAppendedId(MOVIE_REVIEWS_CONTENT_URI, id);
        }
    }

    /**
     * Inner class defining the contents of the movie credit table.
     */
    public static final class MovieCreditsEntry
            implements BaseColumns {
        /**
         * Movie reviews Table name.
         */
        public static String MOVIE_CREDITS_TABLE_NAME = "movie_credits";

        /**
         * Unique URI for the Movie credits table.
         */
        public static final Uri MOVIE_CREDITS_CONTENT_URI =
                BASE_URI.buildUpon()
                        .appendPath(MOVIE_CREDITS_TABLE_NAME)
                        .build();

        /**
         * MIME type for multiple Movie credits rows
         */
        public static final String MOVIE_CREDITS_ITEMS =
                MIME_TYPE_DIR
                        + AUTHORITY
                        + "/"
                        + MOVIE_CREDITS_TABLE_NAME;

        /**
         * MIME type for a single Movie credits row.
         */
        public static final String MOVIE_CREDITS_ITEM =
                MIME_TYPE_ITEM
                        + AUTHORITY
                        + "/"
                        + MOVIE_CREDITS_TABLE_NAME;

        /*
         * Movie credits Table's Columns
         */
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_POSTER_PATH = "poster_path";


        /**
         * Return a URI that points to the row containing the given ID.
         */
        public static Uri buildRowAccessUri(Long id) {
            return ContentUris.withAppendedId(MOVIE_CREDITS_CONTENT_URI, id);
        }
    }
}
