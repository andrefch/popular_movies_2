package br.com.andrefch.popularmoviesii.data.repository.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Author: andrech
 * Date: 19/02/18
 */

public class MovieContentProvider extends ContentProvider {

    private static final int CODE_MOVIE = 100;
    private static final int CODE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = createUriMatcher();

    private MovieDatabaseHelper mMovieDatabaseHelper;

    //region Override Methods
    @Override
    public boolean onCreate() {
        mMovieDatabaseHelper = new MovieDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                table = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case CODE_MOVIE_WITH_ID:
                table = MovieContract.MovieEntry.TABLE_NAME;
                selection = String.format("(%s = ?)", MovieContract.MovieEntry._ID);
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown URI: %s", uri));
        }

        final SQLiteDatabase database = mMovieDatabaseHelper.getReadableDatabase();
        final Cursor cursor = database.query(table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        if ((cursor != null) && (getContext() != null)) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                table = MovieContract.MovieEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown URI: %s", uri));
        }

        final SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();

        final long id = database.insert(table, null, values);

        if ((getContext() != null) && (id > 0)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return uri.buildUpon()
                .appendEncodedPath(String.valueOf(id))
                .build();
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] listValues) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                table = MovieContract.MovieEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown URI: %s", uri));
        }

        final SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();

        int rowsInserted = 0;

        try {
            database.beginTransaction();

            for (ContentValues values : listValues) {
                if (database.insert(table, null, values) > 0) {
                    rowsInserted++;
                }
            }

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        if ((getContext() != null) && (rowsInserted > 0)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsInserted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_WITH_ID:
                table = MovieContract.MovieEntry.TABLE_NAME;
                selection = String.format("(%s = ?)", MovieContract.MovieEntry._ID);
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown URI: %s", uri));
        }

        final SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();

        final int rowsAffected = database.update(table, values, selection, selectionArgs);
        if ((getContext() != null) && (rowsAffected > 0)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsAffected;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        String table;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                table = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case CODE_MOVIE_WITH_ID:
                table = MovieContract.MovieEntry.TABLE_NAME;
                selection = String.format("(%s = ?)", MovieContract.MovieEntry._ID);
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unknown URI: %s", uri));
        }

        final SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();

        final int rowsAffected = database.delete(table, selection, selectionArgs);
        if ((getContext() != null) && (rowsAffected > 0)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsAffected;
    }
    //endregion

    //region Private Methods
    private static UriMatcher createUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.Paths.PATH_MOVIE, CODE_MOVIE);
        matcher.addURI(authority, MovieContract.Paths.PATH_MOVIE_WITH_ID, CODE_MOVIE_WITH_ID);

        return matcher;
    }
    //endregion
}
