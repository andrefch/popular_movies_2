package br.com.andrefch.popularmoviesii.sync;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public abstract class BaseAsyncTaskLoader<D> extends AsyncTaskLoader<AsyncTaskLoaderResult<D>> {

    private AsyncTaskLoaderResult<D> mCacheData;

    public BaseAsyncTaskLoader(Context context) {
        super(context);
        mCacheData = null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mCacheData != null) {
            Log.d(this.getClass().getSimpleName(), "Returning cache data.");
            deliverResult(mCacheData);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(AsyncTaskLoaderResult<D> data) {
        if ((data != null) && (data.isSuccess())) {
            mCacheData = data;
        }
        super.deliverResult(data);
    }

    public interface LoaderCallbacks<D> extends LoaderManager.LoaderCallbacks<AsyncTaskLoaderResult<D>> {

    }
}
