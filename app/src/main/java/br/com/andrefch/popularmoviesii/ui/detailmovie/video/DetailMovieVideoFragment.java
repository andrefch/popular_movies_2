package br.com.andrefch.popularmoviesii.ui.detailmovie.video;


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
import br.com.andrefch.popularmoviesii.data.model.Video;
import br.com.andrefch.popularmoviesii.databinding.FragmentDetailMovieVideoBinding;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;
import br.com.andrefch.popularmoviesii.ui.detailmovie.BaseDetailMovieFragment;
import br.com.andrefch.popularmoviesii.ui.detailmovie.DetailMovieActivity;
import br.com.andrefch.popularmoviesii.ui.detailmovie.review.DetailMovieReviewFragment;
import br.com.andrefch.popularmoviesii.utilities.YoutubeUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovieVideoFragment extends BaseDetailMovieFragment
        implements DetailMovieVideoAdapter.OnVideoSelectedListener,
        BaseAsyncTaskLoader.LoaderCallbacks<List<Video>> {

    private static final String TAG = DetailMovieReviewFragment.class.getSimpleName();

    private FragmentDetailMovieVideoBinding mBinding;
    private DetailMovieVideoAdapter mAdapter;

    public static DetailMovieVideoFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("Movie cannot be null.");
        }

        DetailMovieVideoFragment fragment = new DetailMovieVideoFragment();
        fragment.setArguments(fragment.generateArguments(movie));

        return fragment;
    }

    //region Override Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail_movie_video,
                container,
                false);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(DetailMovieVideoTaskLoader.LOADER_ID,
                DetailMovieVideoTaskLoader.createBundleArgs(mMovie.getMovieId()),
                this);
    }
    //endregion

    //region Public Methods
    public Video getFirstVideo() {
        if ((mAdapter == null) || (mAdapter.getItemCount() == 0)) {
            return null;
        }

        return mAdapter.getVideos().get(0);
    }
    //endregion

    //region Private Methods
    private void initViews() {
        mBinding.emptyState.iconImageView.setImageResource(R.drawable.ic_no_video_gray);
        mBinding.emptyState.messageTextView.setText(R.string.empty_state_no_video);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        mBinding.videoRecyclerView.setLayoutManager(layoutManager);
        mBinding.videoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.videoRecyclerView.setHasFixedSize(true);

        mAdapter = new DetailMovieVideoAdapter(this);
        mBinding.videoRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Movie ID: " + String.valueOf(mMovie.getMovieId()));
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(DetailMovieVideoTaskLoader.LOADER_ID,
                DetailMovieVideoTaskLoader.createBundleArgs(mMovie.getMovieId()),
                this);
    }

    private void changeLayout(boolean showData) {
        mBinding.videoRecyclerView.setVisibility(showData ? View.VISIBLE : View.INVISIBLE);
        mBinding.emptyState.getRoot().setVisibility(!showData ? View.VISIBLE : View.INVISIBLE);
    }
    //endregion

    //region OnVideoSelectedListener Methods
    @Override
    public void onVideoSelected(Video video) {
        if (video != null) {
            if (!YoutubeUtils.openVideo(getContext(), video.getKey())) {
                DetailMovieActivity activity;
                if ((activity = getDetailMovieActivity()) != null) {
                    activity.showSnackMessage(R.string.open_youtube_error);
                }
            }
        }
    }
    //endregion

    //region LoaderCallbacks Methods
    @Override
    public Loader<AsyncTaskLoaderResult<List<Video>>> onCreateLoader(int id, Bundle args) {
        return new DetailMovieVideoTaskLoader(getContext(), args);
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoaderResult<List<Video>>> loader, AsyncTaskLoaderResult<List<Video>> result) {
        if ((result != null) && (result.isSuccess())) {
            mAdapter.setVideos(result.getResult());
        } else {
            mAdapter.setVideos(null);
            DetailMovieActivity activity;
            if ((activity = getDetailMovieActivity()) != null) {
                activity.showSnackMessage(R.string.detail_movie_video_loading_error,
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
    public void onLoaderReset(Loader<AsyncTaskLoaderResult<List<Video>>> loader) {

    }
    //endregion
}
