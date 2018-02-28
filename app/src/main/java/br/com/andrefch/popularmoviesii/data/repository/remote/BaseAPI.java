package br.com.andrefch.popularmoviesii.data.repository.remote;

import android.net.Uri;

import br.com.andrefch.popularmoviesii.BuildConfig;

/**
 * Author: andrech
 * Date: 16/02/18
 */

class BaseAPI {

    private static final String PATH_MOVIE = "movie";
    private static final String QUERY_PARAMETER_API_KEY = "api_key";

    private BaseAPI() {
    }

    static Uri getBaseUri() {
        return Uri.parse(BuildConfig.SERVER_BASE_URL)
                .buildUpon()
                .appendEncodedPath(PATH_MOVIE)
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.SERVER_API_KEY)
                .build();
    }
}
