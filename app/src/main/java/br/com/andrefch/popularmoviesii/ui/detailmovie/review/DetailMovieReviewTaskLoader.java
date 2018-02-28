package br.com.andrefch.popularmoviesii.ui.detailmovie.review;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Review;
import br.com.andrefch.popularmoviesii.data.repository.remote.ReviewAPI;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;

/**
 * Author: andrech
 * Date: 18/02/18
 */

class DetailMovieReviewTaskLoader extends BaseAsyncTaskLoader<List<Review>> {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    static final int LOADER_ID = 102;

    private long mMovieId = 0L;

    DetailMovieReviewTaskLoader(Context context, Bundle args) {
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
    public AsyncTaskLoaderResult<List<Review>> loadInBackground() {
        try {
            return new AsyncTaskLoaderResult<>(ReviewAPI.getReviews(mMovieId));
        } catch (Exception e) {
            return new AsyncTaskLoaderResult<>(e);
        }
    }
}
