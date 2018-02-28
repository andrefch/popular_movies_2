package br.com.andrefch.popularmoviesii.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Author: andrech
 * Date: 18/02/18
 */

public class YoutubeUtils {

    private static final Uri YOUTUBE_BASE_URI = Uri.parse("https://www.youtube.com/watch");
    private static final String QUERY_PARAM_VIDEO = "v";

    private YoutubeUtils() {
    }

    public static boolean openVideo(Context context, final String key) {
        if ((context == null) || TextUtils.isEmpty(key)) {
            return false;
        }

        Uri uri = getUrlVideo(key);

        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }

        context.startActivity(intent);
        return true;
    }

    public static Uri getUrlVideo(String key) {
        return YOUTUBE_BASE_URI.buildUpon()
                .appendQueryParameter(QUERY_PARAM_VIDEO, key)
                .build();
    }
}
