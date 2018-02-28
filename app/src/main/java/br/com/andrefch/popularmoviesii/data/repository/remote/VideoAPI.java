package br.com.andrefch.popularmoviesii.data.repository.remote;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import br.com.andrefch.popularmoviesii.data.mapper.VideoMapper;
import br.com.andrefch.popularmoviesii.data.model.Video;
import br.com.andrefch.popularmoviesii.utilities.NetworkUtils;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class VideoAPI {

    private static final String TAG = VideoAPI.class.getSimpleName();

    private static final Uri BASE_URI = BaseAPI.getBaseUri();
    private static final String VIDEO_PATH = "videos";

    private VideoAPI() {
    }

    public static List<Video> getVideos(long movieId) throws APIException {
        try {
            final URL url = new URL(BASE_URI.buildUpon()
                    .appendEncodedPath(String.valueOf(movieId))
                    .appendEncodedPath(VIDEO_PATH)
                    .build()
                    .toString());

            Log.d(TAG, url.toString());

            final JSONObject response = new JSONObject(NetworkUtils.getResponseFromUrl(url));
            final JSONArray jsonVideos = response.optJSONArray("results");

            return VideoMapper.convertJsonToVideoList(jsonVideos);
        } catch (Exception e) {
            final String message = String.format(Locale.getDefault(), "Failed to get videos from movieID %d.", movieId);
            Log.e(TAG, message, e);
            throw new APIException(message, e);
        }
    }
}
