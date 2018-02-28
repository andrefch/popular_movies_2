package br.com.andrefch.popularmoviesii.data.repository.remote;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import br.com.andrefch.popularmoviesii.data.mapper.ReviewMapper;
import br.com.andrefch.popularmoviesii.data.model.Review;
import br.com.andrefch.popularmoviesii.utilities.NetworkUtils;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class ReviewAPI {

    private static final String TAG = ReviewAPI.class.getSimpleName();

    private static final Uri BASE_URI = BaseAPI.getBaseUri();
    private static final String REVIEW_PATH = "reviews";

    private ReviewAPI() {
    }

    public static List<Review> getReviews(long movieId) throws APIException {
        try {
            final URL url = new URL(BASE_URI.buildUpon()
                    .appendEncodedPath(String.valueOf(movieId))
                    .appendEncodedPath(REVIEW_PATH)
                    .build()
                    .toString());

            Log.d(TAG, url.toString());

            final JSONObject response = new JSONObject(NetworkUtils.getResponseFromUrl(url));
            final JSONArray jsonReviews = response.optJSONArray("results");

            return ReviewMapper.convertJsonToReviewList(jsonReviews);
        } catch (Exception e) {
            final String message = String.format(Locale.getDefault(), "Failed to get reviews from movieID %d.", movieId);
            Log.e(TAG, message, e);
            throw new APIException(message, e);
        }
    }
}
