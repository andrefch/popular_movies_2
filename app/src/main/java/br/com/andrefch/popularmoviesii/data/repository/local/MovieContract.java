package br.com.andrefch.popularmoviesii.data.repository.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Author: andrech
 * Date: 19/02/18
 */

public class MovieContract {

    private MovieContract() {
    }

    static final String CONTENT_AUTHORITY = "br.com.andrefch.popularmovies";
    private static final Uri BASE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(CONTENT_AUTHORITY)
            .build();

    static final class Paths {

        private Paths() {
        }

        static final String PATH_MOVIE = "movie";
        static final String PATH_MOVIE_WITH_ID = String.format("%s/#", PATH_MOVIE);
    }

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendEncodedPath(Paths.PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE= "title";
        public static final String COLUMN_ORIGINAL_TITLE= "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
    }
}
