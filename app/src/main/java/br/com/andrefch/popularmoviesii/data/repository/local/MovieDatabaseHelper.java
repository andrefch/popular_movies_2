package br.com.andrefch.popularmoviesii.data.repository.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: andrech
 * Date: 19/02/18
 */

class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE %1$s (%2$s);";
    private static final String DROP_TABLE_COMMAND = "DROP TABLE IF EXISTS %s";

    MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //region Override Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateMovieTableCommand());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format(DROP_TABLE_COMMAND, MovieContract.MovieEntry.TABLE_NAME));

        onCreate(db);
    }
    //endregion

    //region Private Methods
    private String getCreateMovieTableCommand() {
        final String fields = MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL,"
                + MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NULL,"
                + MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NULL,"
                + MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NULL,"
                + MovieContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NULL,"
                + MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NULL,"
                + MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL NULL,"
                + MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NULL,"
                + MovieContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NULL,"
                + MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " REAL NULL,"
                + "UNIQUE (" + MovieContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE";

        return String.format(CREATE_TABLE_COMMAND,
                MovieContract.MovieEntry.TABLE_NAME,
                fields);
    }
    //endregion
}
