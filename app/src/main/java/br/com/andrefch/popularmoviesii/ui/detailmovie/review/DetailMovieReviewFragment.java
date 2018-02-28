package br.com.andrefch.popularmoviesii.ui.detailmovie.review;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.data.model.Review;
import br.com.andrefch.popularmoviesii.databinding.FragmentDetailMovieReviewBinding;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;
import br.com.andrefch.popularmoviesii.ui.detailmovie.BaseDetailMovieFragment;
import br.com.andrefch.popularmoviesii.ui.detailmovie.DetailMovieActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovieReviewFragment extends BaseDetailMovieFragment
        implements BaseAsyncTaskLoader.LoaderCallbacks<List<Review>> {

    private static final String TAG = DetailMovieReviewFragment.class.getSimpleName();

    private FragmentDetailMovieReviewBinding mBinding;
    private DetailMovieReviewAdapter mAdapter;

    public static DetailMovieReviewFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("Movie cannot be null.");
        }

        DetailMovieReviewFragment fragment = new DetailMovieReviewFragment();
        fragment.setArguments(fragment.generateArguments(movie));

        return fragment;
    }

    //region Override Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail_movie_review,
                container,
                false);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(DetailMovieReviewTaskLoader.LOADER_ID,
                DetailMovieReviewTaskLoader.createBundleArgs(mMovie.getMovieId()),
                this);
    }
    //endregion

    //region Private Methods
    private void initViews() {
        mBinding.emptyState.iconImageView.setImageResource(R.drawable.ic_no_comment_gray);
        mBinding.emptyState.messageTextView.setText(R.string.empty_state_no_review);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        mBinding.reviewRecyclerView.setLayoutManager(layoutManager);
        mBinding.reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.reviewRecyclerView.setHasFixedSize(false);

        mAdapter = new DetailMovieReviewAdapter();
        mBinding.reviewRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Movie ID: " + String.valueOf(mMovie.getMovieId()));
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(DetailMovieReviewTaskLoader.LOADER_ID,
                DetailMovieReviewTaskLoader.createBundleArgs(mMovie.getMovieId()),
                this);
    }

    private void changeLayout(boolean showData) {
        mBinding.reviewRecyclerView.setVisibility(showData ? View.VISIBLE : View.INVISIBLE);
        mBinding.emptyState.getRoot().setVisibility(!showData ? View.VISIBLE : View.INVISIBLE);
    }
    //endregion

    //region LoaderCallbacks Methods
    @Override
    public Loader<AsyncTaskLoaderResult<List<Review>>> onCreateLoader(int id, Bundle args) {
        return new DetailMovieReviewTaskLoader(getContext(), args);
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoaderResult<List<Review>>> loader, AsyncTaskLoaderResult<List<Review>> result) {
        if ((result != null) && (result.isSuccess())) {
            mAdapter.setReviews(result.getResult());
        } else {
            mAdapter.setReviews(null);
            if (getActivity() instanceof DetailMovieActivity) {
                DetailMovieActivity activity = (DetailMovieActivity) getActivity();
                activity.showSnackMessage(R.string.detail_movie_review_loading_error,
                        R.string.try_again,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                restartLoader();
                            }
                        });
            }
        }
        changeLayout(mAdapter.getItemCount() > 0);
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskLoaderResult<List<Review>>> loader) {

    }
    //endregion
}
