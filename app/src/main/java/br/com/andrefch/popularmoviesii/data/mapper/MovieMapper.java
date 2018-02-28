package br.com.andrefch.popularmoviesii.data.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.data.repository.local.MovieContract;
import br.com.andrefch.popularmoviesii.utilities.DateUtils;

/**
 * Author: andrech
 * Date: 01/02/18
 */

public class MovieMapper {

    private static final String MOVIE_API_DATE_FORMAT = "yyyy-MM-dd";

    private MovieMapper() {
    }

    //region Cursor
    private static Movie convertCursorToMovie(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        final Movie movie = new Movie();

        movie.setId(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID)));
        movie.setMovieId(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setVoteCount(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        movie.setReleaseDate(new Date(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))));

        return movie;
    }

    public static List<Movie> convertCursorToListMovie(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        final List<Movie> movies = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                final Movie movie = convertCursorToMovie(cursor);
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        return movies;
    }
    //endregion

    //region ContentValues
    public static ContentValues convertMovieToContentValues(Movie movie) {
        if (movie == null) {
            return null;
        }

        final ContentValues values = new ContentValues();

        if (movie.getId() > 0) {
            values.put(MovieContract.MovieEntry._ID, movie.getId());
        }
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        if (movie.getReleaseDate() != null) {
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate().getTime());
        }

        return values;
    }
    //endregion

    //region JSON
    private static Movie convertJsonToMovie(JSONObject json) {
        if (json == null) {
            return null;
        }

        final Movie movie = new Movie();
        movie.setMovieId(json.optLong(JSONFields.MOVIE_ID));
        movie.setTitle(json.optString(JSONFields.TITLE));
        movie.setOriginalTitle(json.optString(JSONFields.ORIGINAL_TITLE));
        movie.setOverview(json.optString(JSONFields.OVERVIEW));
        movie.setVoteCount(json.optLong(JSONFields.VOTE_COUNT));
        movie.setVoteAverage((float) json.optDouble(JSONFields.VOTE_AVERAGE));
        movie.setPopularity((float) json.optDouble(JSONFields.POPULARITY));
        movie.setPosterPath(json.optString(JSONFields.POSTER_PATH));
        movie.setBackdropPath(json.optString(JSONFields.BACKDROP_PATH));
        movie.setReleaseDate(DateUtils.stringToDate(
                MOVIE_API_DATE_FORMAT,
                json.optString(JSONFields.RELEASE_DATE)));

        return movie;
    }

    public static List<Movie> convertJsonToMovieList(JSONArray json) {
        if (json == null) {
            return null;
        }

        final List<Movie> movies = new ArrayList<>();

        for (int index = 0; index < json.length(); index++) {
            final JSONObject jsonMovie = json.optJSONObject(index);
            movies.add(convertJsonToMovie(jsonMovie));
        }

        return movies;
    }

    private static class JSONFields {
        private JSONFields() {
        }

        private static final String MOVIE_ID = "id";
        private static final String TITLE = "title";
        private static final String ORIGINAL_TITLE = "original_title";
        private static final String OVERVIEW = "overview";
        private static final String VOTE_COUNT = "vote_count";
        private static final String VOTE_AVERAGE = "vote_average";
        private static final String POPULARITY = "popularity";
        private static final String POSTER_PATH = "poster_path";
        private static final String BACKDROP_PATH = "backdrop_path";
        private static final String RELEASE_DATE = "release_date";
    }
    //endregion
}
