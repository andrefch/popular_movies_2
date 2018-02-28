package br.com.andrefch.popularmoviesii.data.repository.remote;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import br.com.andrefch.popularmoviesii.data.mapper.MovieMapper;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.utilities.NetworkUtils;

/**
 * Author: andrech
 * Date: 01/02/18
 */

public class MovieAPI {

    private static final String TAG = MovieAPI.class.getSimpleName();

    private static final Uri BASE_URI = BaseAPI.getBaseUri();

    private MovieAPI() {
    }

    public static List<Movie> getMovies(String path) throws APIException {
        try {
            final URL url = new URL(BASE_URI.buildUpon()
                    .appendEncodedPath(path)
                    .build()
                    .toString());

            Log.d(TAG, url.toString());

            final JSONObject response = new JSONObject(NetworkUtils.getResponseFromUrl(url));
            final JSONArray jsonMovies = response.optJSONArray("results");

            return MovieMapper.convertJsonToMovieList(jsonMovies);
        } catch (Exception e) {
            final String message = String.format(Locale.getDefault(), "Failed to get movies with path: %s", path);
            Log.e(TAG, message, e);
            throw new APIException(message, e);
        }
    }
}
