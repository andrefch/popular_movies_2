package br.com.andrefch.popularmoviesii.ui.detailmovie.video;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Video;
import br.com.andrefch.popularmoviesii.data.repository.remote.VideoAPI;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;

/**
 * Author: andrech
 * Date: 18/02/18
 */

class DetailMovieVideoTaskLoader extends BaseAsyncTaskLoader<List<Video>> {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    static final int LOADER_ID = 101;

    private long mMovieId = 0L;

    DetailMovieVideoTaskLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            mMovieId = args.getLong(EXTRA_MOVIE_ID);
        }
    }

    static Bundle createBundleArgs(long movieId) {
        final Bundle args = new Bundle();
        args.putLong(EXTRA_MOVIE_ID, movieId);
        return args;
    }

    @Override
    public AsyncTaskLoaderResult<List<Video>> loadInBackground() {
        try {
            return new AsyncTaskLoaderResult<>(VideoAPI.getVideos(mMovieId));
        } catch (Exception e) {
            return new AsyncTaskLoaderResult<>(e);
        }
    }
}
