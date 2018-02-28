package br.com.andrefch.popularmoviesii.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Author: andrech
 * Date: 01/02/18
 */

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        Scanner scanner = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        NetworkInfo info = manager.getActiveNetworkInfo();
        return ((info != null) && (info.isConnectedOrConnecting()));
    }
}
