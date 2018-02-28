package br.com.andrefch.popularmoviesii.utilities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateFormat;

import br.com.andrefch.popularmoviesii.BuildConfig;
import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Movie;

/**
 * Author: andrech
 * Date: 05/02/18
 */

public class MovieUtils {

    private MovieUtils() {
    }

    public static Uri buildPosterUri(Context context, Movie movie) {
        if ((context == null) || (movie == null)
                || (TextUtils.isEmpty(movie.getPosterPath()))) {
            return null;
        }

        return Uri.parse(BuildConfig.SERVER_IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(context.getString(R.string.list_movie_url_path_image_size))
                .appendEncodedPath(movie.getPosterPath())
                .build();
    }

    public static Uri buildBackdropUri(Context context, Movie movie) {
        if ((context == null) || (movie == null)
                || (TextUtils.isEmpty(movie.getBackdropPath()))) {
            return null;
        }

        return Uri.parse(BuildConfig.SERVER_IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(context.getString(R.string.list_movie_url_path_image_size))
                .appendEncodedPath(movie.getBackdropPath())
                .build();
    }

    public static String getReleaseDate(Context context, Movie movie) {
        if (context == null) {
            return null;
        }

        if ((movie == null) || (movie.getReleaseDate() == null)) {
            return context.getString(R.string.release_date_movie_empty);
        }

        return DateFormat.getDateFormat(context)
                .format(movie.getReleaseDate());
    }

    public static boolean isLocalPath(Context context, String path) {
        if ((context == null) || (TextUtils.isEmpty(path))) {
            return false;
        }

        final String localPath = context.getString(R.string.list_movie_sort_favorites_path);
        return localPath.equals(path);
    }
}
