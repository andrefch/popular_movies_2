package br.com.andrefch.popularmoviesii.utilities;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: andrech
 * Date: 02/02/18
 */

public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    private DateUtils() {
    }

    public static Date stringToDate(String format, String date) {
        if (TextUtils.isEmpty(format) || TextUtils.isEmpty(date)) {
            return null;
        }

        try {
            DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            return formatter.parse(date);
        } catch (ParseException e) {
            Log.e(TAG,
                    String.format("Failed to convert \"%s\" to date.", date),
                    e);
            return null;
        }
    }
}
