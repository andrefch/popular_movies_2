package br.com.andrefch.popularmoviesii.ui.listmovie;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.mapper.MovieMapper;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.data.repository.local.MovieContract;
import br.com.andrefch.popularmoviesii.data.repository.remote.MovieAPI;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;
import br.com.andrefch.popularmoviesii.utilities.MovieUtils;
import br.com.andrefch.popularmoviesii.utilities.NetworkUtils;
import br.com.andrefch.popularmoviesii.utilities.exception.NoNetworkConnectionException;

/**
 * Author: andrech
 * Date: 05/02/18
 */

class ListMovieAsyncTaskLoader extends BaseAsyncTaskLoader<List<Movie>> {

    private static final String TAG = ListMovieAsyncTaskLoader.class.getSimpleName();

    private static final String EXTRA_MOVIE_PATH = "EXTRA_MOVIE_PATH";
    static final int LOADER_ID = 100;

    private String mPath;

    ListMovieAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        mPath = context.getString(R.string.list_movie_sort_popularity_path);
        if (args != null) {
            mPath = args.getString(EXTRA_MOVIE_PATH,
                    mPath);
        }
    }

    static Bundle createBundleArgs(String path) {
        final Bundle args = new Bundle();
        args.putString(EXTRA_MOVIE_PATH, path);
        return args;
    }

    @Override
    protected void onStartLoading() {
        if ((!MovieUtils.isLocalPath(getContext(), mPath))
            && (!NetworkUtils.isNetworkConnected(getContext()))) {
            AsyncTaskLoaderResult<List<Movie>> result = new AsyncTaskLoaderResult<>(new NoNetworkConnectionException());
            deliverResult(result);
        } else {
            super.onStartLoading();
        }
    }

    @Override
    public AsyncTaskLoaderResult<List<Movie>> loadInBackground() {
        Log.d(TAG, "Loading movies...");
        try {
            final List<Movie> movies;
            if (MovieUtils.isLocalPath(getContext(), mPath)) {
                Cursor cursor = null;
                try {
                    cursor = getContext().getContentResolver()
                            .query(MovieContract.MovieEntry.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null);
                    movies = MovieMapper.convertCursorToListMovie(cursor);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            } else {
                movies = MovieAPI.getMovies(mPath);
            }
            return new AsyncTaskLoaderResult<>(movies);
        } catch (Exception e) {
            return new AsyncTaskLoaderResult<>(e);
        }
    }
}
