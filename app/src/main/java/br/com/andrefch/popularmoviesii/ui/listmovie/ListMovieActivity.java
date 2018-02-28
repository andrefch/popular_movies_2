package br.com.andrefch.popularmoviesii.ui.listmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.databinding.ActivityListMovieBinding;
import br.com.andrefch.popularmoviesii.sync.AsyncTaskLoaderResult;
import br.com.andrefch.popularmoviesii.sync.BaseAsyncTaskLoader;
import br.com.andrefch.popularmoviesii.ui.detailmovie.DetailMovieActivity;
import br.com.andrefch.popularmoviesii.utilities.MovieUtils;
import br.com.andrefch.popularmoviesii.utilities.exception.NoNetworkConnectionException;

public class ListMovieActivity extends AppCompatActivity implements
        BaseAsyncTaskLoader.LoaderCallbacks<List<Movie>>,
        ListMovieAdapter.OnMovieSelectedListener, Spinner.OnItemSelectedListener {

    public static final String ACTION_REFRESH_LIST_MOVIE = "ACTION_REFRESH_LIST_MOVIE";

    private static final String EXTRA_LIST_MOVIE_SORT_PATH = "EXTRA_LIST_MOVIE_SORT_PATH";

    private ActivityListMovieBinding mBinding;

    private String mSortPath;
    private ListMovieAdapter mAdapter;
    private ListMovieSortAdapter mSortAdapter;

    private BroadcastReceiver mRefreshReceiver;

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_movie);

        setupFilterSpinner();
        setupRecyclerView();
        setupRefreshBroadcastReceiver();

        mSortPath = getString(R.string.list_movie_sort_popularity_path);
        if ((savedInstanceState != null) && (savedInstanceState.containsKey(EXTRA_LIST_MOVIE_SORT_PATH))) {
            mSortPath = savedInstanceState.getString(EXTRA_LIST_MOVIE_SORT_PATH, mSortPath);
        }

        getSupportLoaderManager().initLoader(ListMovieAsyncTaskLoader.LOADER_ID,
                ListMovieAsyncTaskLoader.createBundleArgs(mSortPath),
                this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBroadcastManager().unregisterReceiver(mRefreshReceiver);
    }

    //endregion

    //region Private Methods
    private void setupRecyclerView() {
        mBinding.listMovieRecyclerView.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.list_movie_number_of_columns)));

        mBinding.listMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.listMovieRecyclerView.setHasFixedSize(true);

        mAdapter = new ListMovieAdapter(this);
        mBinding.listMovieRecyclerView.setAdapter(mAdapter);
    }

    private void setupFilterSpinner() {
        mSortAdapter = new ListMovieSortAdapter(this);
        mBinding.filterMovieSpinner.setAdapter(mSortAdapter);

        mBinding.filterMovieSpinner.setOnItemSelectedListener(this);
    }

    private void setupRefreshBroadcastReceiver() {
        mRefreshReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (MovieUtils.isLocalPath(context, mSortPath)) {
                    restartLoader();
                }
            }
        };

        getBroadcastManager().registerReceiver(
                mRefreshReceiver,
                new IntentFilter(ACTION_REFRESH_LIST_MOVIE));
    }

    private void restartLoader() {
        getSupportLoaderManager().restartLoader(ListMovieAsyncTaskLoader.LOADER_ID,
                ListMovieAsyncTaskLoader.createBundleArgs(mSortPath),
                this);
    }

    private LocalBroadcastManager getBroadcastManager() {
        return LocalBroadcastManager.getInstance(this);
    }

    private void changeLayout(boolean showData) {
        mBinding.listMovieRecyclerView.setVisibility(showData ? View.VISIBLE : View.INVISIBLE);
        mBinding.emptyState.getRoot().setVisibility(!showData ? View.VISIBLE : View.INVISIBLE);
    }

    private void configureEmptyState(boolean noConnection) {
        if (noConnection) {
            mBinding.emptyState.iconImageView.setImageResource(R.drawable.ic_network_connection_off_gray);
            mBinding.emptyState.messageTextView.setText(R.string.empty_state_no_connection);
        } else {
            mBinding.emptyState.iconImageView.setImageResource(R.drawable.ic_video_flat);
            mBinding.emptyState.messageTextView.setText(R.string.empty_state_no_movie);
        }
    }
    //endregion

    //region OnMovieSelectedListener Methods
    @Override
    public void onMovieSelected(Movie movie) {
        if (movie == null) {
            return;
        }
        final Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
    //endregion

    //region Spinner.OnItemSelectedListener Methods
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSortPath = mSortAdapter.getPath(position);
        restartLoader();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //endregion

    //region LoaderManager.LoaderCallbacks Methods
    @Override
    public Loader<AsyncTaskLoaderResult<List<Movie>>> onCreateLoader(int id, Bundle args) {
        return new ListMovieAsyncTaskLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<AsyncTaskLoaderResult<List<Movie>>> loader, AsyncTaskLoaderResult<List<Movie>> data) {
        if ((data != null) && (data.isSuccess())) {
            mAdapter.setMovies(data.getResult());
        } else {
            mAdapter.setMovies(null);
            configureEmptyState((data != null)
                    && (data.getException() instanceof NoNetworkConnectionException));
            Snackbar.make(mBinding.getRoot(),
                    R.string.list_movie_loading_error,
                    Snackbar.LENGTH_LONG)
                    .setAction(R.string.try_again,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    restartLoader();
                                }
                            })
                    .show();
        }
        changeLayout(mAdapter.getItemCount() > 0);
    }

    @Override
    public void onLoaderReset(Loader<AsyncTaskLoaderResult<List<Movie>>> loader) {

    }
    //endregion
}
