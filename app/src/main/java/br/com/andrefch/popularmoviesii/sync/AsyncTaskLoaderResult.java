package br.com.andrefch.popularmoviesii.sync;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class AsyncTaskLoaderResult<D> {

    private final boolean mSuccess;
    private final D mResult;
    private final Exception mException;

    public AsyncTaskLoaderResult(D result) {
        super();
        mSuccess = true;
        mResult = result;
        mException = null;
    }

    public AsyncTaskLoaderResult(Exception exception) {
        super();
        mSuccess = false;
        mResult = null;
        mException = exception;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public D getResult() {
        return mResult;
    }

    public Exception getException() {
        return mException;
    }
}
