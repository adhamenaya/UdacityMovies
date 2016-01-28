package com.adhamenaya.moviesapp.provider;

/**
 * Created by AENAYA on 8/21/2015.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider used to store information about movie data
 * returned from the Movie web services.
 */
public class MovieProvider extends ContentProvider {

    private final String TAG = getClass().getCanonicalName();

    /**
     * UriMatcher code for the Movie table.
     */
    public static final int MOVIE_ITEMS = 100;
    public static final int MOVIE_ITEM = 110;

    /**
     * UriMatcher code for the Movie reviews table.
     */
    public static final int MOVIE_REVIEWS_ITEMS = 200;
    public static final int MOVIE_REVIEWS_ITEM = 210;

    /**
     * UriMatcher code for the Movie credits table.
     */
    public static final int MOVIE_CREDITS_ITEMS = 300;
    public static final int MOVIE_CREDITS_ITEM = 310;

    /**
     * UriMatcher code for the accessing all the movies in the database.
     */
    public static final int ACCESS_ALL_MOVIES_ITEM = 400;

    /**
     * UriMatcher that is used to dispatch the incoming URIs into
     * requests.
     */
    public static final UriMatcher sUriMatcher =
            buildUriMatcher();

    /**
     * Build the UriMatcher for this Content Provider.
     */
    public static UriMatcher buildUriMatcher() {
        // Add default 'no match' result to matcher.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Initialize the matcher with the URIs used to access each
        // table.
        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieEntry.MOVIES_TABLE_NAME, MOVIE_ITEMS);
        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieEntry.MOVIES_TABLE_NAME
                        + "/#", MOVIE_ITEM);

        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME,
                MOVIE_REVIEWS_ITEMS);
        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME
                        + "/#", MOVIE_REVIEWS_ITEM);

        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME,
                MOVIE_CREDITS_ITEMS);
        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME
                        + "/#", MOVIE_CREDITS_ITEM);

        matcher.addURI(MovieContract.AUTHORITY,
                MovieContract.ACCESS_ALL_MOVIES_PATH, ACCESS_ALL_MOVIES_ITEM);

        return matcher;
    }

    /*
     * Constants referencing the Contract class.  Used for convenience
     * to avoid having to retype long constant names.
     */

    private static final String MOVIE_TABLE_NAME =
            MovieContract.MovieEntry.MOVIES_TABLE_NAME;

    private static final String MOVIE_REVIEWS_TABLE_NAME =
            MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME;

    private static final String MOVIE_CREDITS_TABLE_NAME =
            MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME;

    /**
     * The database helper that is used to manage the providers
     * database.
     */
    private MovieDatabaseHelper mDatabaseHelper;

    /**
     * Hook method called when the provider is created.
     */
    @Override
    public boolean onCreate() {
        mDatabaseHelper = new MovieDatabaseHelper(getContext());
        return true;
    }

    /**
     * Helper method that appends a given key id to the end of the
     * WHERE statement parameter.
     */
    private static String addKeyIdCheckToWhereStatement(String whereStatement,
                                                        long id) {
        String newWhereStatement;
        if (TextUtils.isEmpty(whereStatement))
            newWhereStatement = "";
        else
            newWhereStatement = whereStatement + " AND ";

        return newWhereStatement
                + " _id = "
                + "'"
                + id
                + "'";
    }

    /**
     * Method called to handle query requests from client
     * applications.
     */
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String whereStatement,
                        String[] whereStatementArgs,
                        String sortOrder) {
        // Create a SQLite query builder that will be modified based
        // on the Uri passed.
        final SQLiteQueryBuilder queryBuilder =
                new SQLiteQueryBuilder();

        // Use the passed Uri to determine how to build the
        // query. This will determine the table that the query will
        // act on and possibly add row qualifications to the WHERE
        // clause.
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ITEMS:
                queryBuilder.setTables(MOVIE_TABLE_NAME);
                break;
            case MOVIE_ITEM:
                queryBuilder.setTables(MOVIE_TABLE_NAME);
                whereStatement = addKeyIdCheckToWhereStatement(whereStatement, ContentUris.parseId(uri));
                break;

            case MOVIE_REVIEWS_ITEMS:
                queryBuilder.setTables(MOVIE_REVIEWS_TABLE_NAME);
                break;
            case MOVIE_REVIEWS_ITEM:
                queryBuilder.setTables(MOVIE_REVIEWS_TABLE_NAME);
                whereStatement = addKeyIdCheckToWhereStatement(whereStatement, ContentUris.parseId(uri));
                break;


            case MOVIE_CREDITS_ITEMS:
                queryBuilder.setTables(MOVIE_CREDITS_TABLE_NAME);
                break;
            case MOVIE_CREDITS_ITEM:
                queryBuilder.setTables(MOVIE_CREDITS_TABLE_NAME);
                whereStatement = addKeyIdCheckToWhereStatement(whereStatement, ContentUris.parseId(uri));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Once the query builder has been initialized based on the
        // provided Uri, use it to query the database.
        final Cursor cursor =
                queryBuilder.query(mDatabaseHelper.getReadableDatabase(),
                        projection,
                        whereStatement,
                        whereStatementArgs,
                        null,    // GROUP BY (not used)
                        null,    // HAVING   (not used)
                        sortOrder);

        // Register to watch a content URI for changes.
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    /**
     * Method called to handle type requests from client applications.
     * It returns the MIME type of the data associated with each URI.
     */
    @Override
    public String getType(Uri uri) {
        // Use the passed Uri to determine what data is being asked
        // for and return the appropriate MIME type
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ITEMS:
                return MovieContract.MovieEntry.MOVIE_ITEMS;
            case MOVIE_ITEM:
                return MovieContract.MovieEntry.MOVIE_ITEM;

            case MOVIE_REVIEWS_ITEMS:
                return MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_ITEMS;
            case MOVIE_REVIEWS_ITEM:
                return MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_ITEM;

            case MOVIE_CREDITS_ITEMS:
                return MovieContract.MovieCreditsEntry.MOVIE_CREDITS_ITEMS;
            case MOVIE_CREDITS_ITEM:
                return MovieContract.MovieCreditsEntry.MOVIE_CREDITS_ITEM;

            case ACCESS_ALL_MOVIES_ITEM:
                return MovieContract.ACCESS_ALL_MOVIES;
            default:
                throw new IllegalArgumentException("Unknown URI "
                        + uri);
        }
    }

    /**
     * Method called to handle insert requests from client
     * applications.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // The table to perform the insert on.
        String table;

        // The Uri containing the inserted row's id that is returned
        // to the caller.
        Uri resultUri;

        // Determine the base Uri to return and the table to insert on
        // using the UriMatcher.
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ITEMS:
                table = MOVIE_TABLE_NAME;
                resultUri = MovieContract.MovieEntry.MOVIE_CONTENT_URI;
                break;

            case MOVIE_REVIEWS_ITEMS:
                table = MOVIE_REVIEWS_TABLE_NAME;
                resultUri = MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_CONTENT_URI;
                break;

            case MOVIE_CREDITS_ITEMS:
                table = MOVIE_CREDITS_TABLE_NAME;
                resultUri = MovieContract.MovieCreditsEntry.MOVIE_CREDITS_CONTENT_URI;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI "
                        + uri);
        }
        // Insert the data into the correct table.
        final long insertRow =
                mDatabaseHelper.getWritableDatabase().insert
                        (table, null, values);

        // Check to ensure that the insertion worked.
        if (insertRow > 0) {
            // Create the result URI.
            Uri newUri = ContentUris.withAppendedId(resultUri,
                    insertRow);

            // Register to watch a content URI for changes.
            getContext().getContentResolver().notifyChange(newUri,
                    null);
            // Register a change to the all-data uri
            getContext().getContentResolver()
                    .notifyChange(MovieContract.ACCESS_ALL_MOVIES_URI, null);
            return newUri;
        } else
            throw new SQLException("Fail to add a new record into " + uri);
    }

    /**
     * Method that handles bulk insert requests.
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        // Fetch the db from the helper.
        final SQLiteDatabase db =
                mDatabaseHelper.getWritableDatabase();

        String dbName;

        // Match the Uri against the table's uris to determine the
        // table in which table to insert the values.
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ITEMS:
                dbName = MovieContract.MovieEntry.MOVIES_TABLE_NAME;
                break;
            case MOVIE_REVIEWS_ITEMS:
                dbName = MovieContract.MovieReviewsEntry.MOVIE_REVIEWS_TABLE_NAME;
                break;
            case MOVIE_CREDITS_ITEMS:
                dbName = MovieContract.MovieCreditsEntry.MOVIE_CREDITS_TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Insert the values into the table in one transaction by
        // beginning a transaction in EXCLUSIVE mode.
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                final long id =
                        db.insert(dbName,
                                null,
                                value);
                if (id != -1)
                    returnCount++;
            }
            // Marks the current transaction as successful.
            db.setTransactionSuccessful();
        } finally {
            // End the transaction
            db.endTransaction();
        }

        // Notifies registered observers that rows were updated and
        // attempt to sync changes to the network.
        getContext().getContentResolver().notifyChange(uri,
                null);
        // Register a change to the all-data URI
        getContext().getContentResolver().notifyChange(MovieContract.ACCESS_ALL_MOVIES_URI, null);
        return returnCount;
    }

    /**
     * Method called to handle update requests from client
     * applications.
     */
    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String whereStatement,
                      String[] whereStatementArgs) {

        return -1;
    }

    /**
     * Method called to handle delete requests from client
     * applications.
     */
    @Override
    public int delete(Uri uri,
                      String whereStatement,
                      String[] whereStatementArgs) {
        // Number of rows deleted.
        int rowsDeleted;

        final SQLiteDatabase db =
                mDatabaseHelper.getWritableDatabase();

        // Delete the appropriate rows based on the Uri. If the URI
        // includes a specific row to delete, add that row to the
        // WHERE statement.
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ITEMS:
                rowsDeleted =
                        db.delete(MOVIE_TABLE_NAME,
                                whereStatement, whereStatementArgs);
                break;
            case MOVIE_ITEM:
                rowsDeleted =
                        db.delete(MOVIE_TABLE_NAME,
                                addKeyIdCheckToWhereStatement
                                        (whereStatement, ContentUris.parseId(uri)),
                                whereStatementArgs);
                break;
            case MOVIE_REVIEWS_ITEMS:
                rowsDeleted =
                        db.delete(MOVIE_REVIEWS_TABLE_NAME,
                                whereStatement, whereStatementArgs);
                break;
            case MOVIE_REVIEWS_ITEM:
                rowsDeleted =
                        db.delete(MOVIE_REVIEWS_TABLE_NAME,
                                addKeyIdCheckToWhereStatement
                                        (whereStatement, ContentUris.parseId(uri)),
                                whereStatementArgs);
                break;
            case MOVIE_CREDITS_ITEMS:
                rowsDeleted =
                        db.delete(MOVIE_CREDITS_TABLE_NAME,
                                whereStatement, whereStatementArgs);
                break;
            case MOVIE_CREDITS_ITEM:
                rowsDeleted =
                        db.delete(MOVIE_CREDITS_TABLE_NAME,
                                addKeyIdCheckToWhereStatement
                                        (whereStatement, ContentUris.parseId(uri)),
                                whereStatementArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "
                        + uri);
        }

        // Register to watch a content URI for changes.
        getContext().getContentResolver().notifyChange(uri,
                null);
        // Register a change to the all-data uri.
        getContext().getContentResolver()
                .notifyChange(MovieContract.ACCESS_ALL_MOVIES_URI, null);

        return rowsDeleted;
    }
}