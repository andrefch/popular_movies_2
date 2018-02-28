package br.com.andrefch.popularmoviesii.ui.detailmovie;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.picasso.Picasso;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.mapper.MovieMapper;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.data.model.Video;
import br.com.andrefch.popularmoviesii.data.repository.local.MovieContract;
import br.com.andrefch.popularmoviesii.databinding.ActivityDetailMovieBinding;
import br.com.andrefch.popularmoviesii.ui.detailmovie.video.DetailMovieVideoFragment;
import br.com.andrefch.popularmoviesii.ui.listmovie.ListMovieActivity;
import br.com.andrefch.popularmoviesii.utilities.MovieUtils;
import br.com.andrefch.popularmoviesii.utilities.YoutubeUtils;

public class DetailMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private static final int LOADER_ID = 104;
    private static final String KEY_MOVIE_ID = "KEY_MOVIE_ID";

    private static final int[] ICON_TABS = new int[] {
            R.drawable.ic_info_outline,
            R.drawable.ic_movie,
            R.drawable.ic_comment
    };

    private static final String[] CURSOR_FIELDS = new String[] {
            MovieContract.MovieEntry._ID
    };

    private ActivityDetailMovieBinding mBinding;
    private DetailMoviePagerAdapter mPagerAdapter;
    private Movie mMovie;

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);

        loadMovie(savedInstanceState);
        startLoader();
        initViews();
        showMovie();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail_movie, menu);

        final int id = ((mMovie != null) && (mMovie.getId() > 0))
                ? R.id.action_favorite
                : R.id.action_unfavorite;

        MenuItem item = menu.findItem(id);
        if (item != null) {
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_favorite:
                addMovieToFavorites();
                return true;
            case R.id.action_unfavorite:
                removeMovieToFavorites();
                return true;
            case R.id.action_share:
                shareMovie();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE, mMovie);
    }
    //endregion

    //region Public Methods
    public void showSnackMessage(@StringRes int messageResId, @StringRes int actionResId,
                                 View.OnClickListener action) {
        Snackbar snackbar = Snackbar.make(mBinding.getRoot(), messageResId, Snackbar.LENGTH_LONG);
        if (action != null) {
            snackbar.setAction(actionResId, action);
        }
        snackbar.show();
    }

    public void showSnackMessage(@StringRes int messageResId) {
        showSnackMessage(messageResId, 0, null);
    }
    //endregion

    //region Private Methods
    private void initViews() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPagerAdapter = new DetailMoviePagerAdapter(getSupportFragmentManager(), mMovie);
        mBinding.detailMovieViewPager.setAdapter(mPagerAdapter);

        mBinding.detailMovieTabLayout.setupWithViewPager(mBinding.detailMovieViewPager);
        setupTabLayoutIcons();
    }

    private void loadMovie(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }

        final Intent intent = getIntent();
        if ((mMovie == null) && (intent != null)) {
            mMovie = intent.getParcelableExtra(EXTRA_MOVIE);
        }
        invalidateOptionsMenu();
    }

    private void showMovie() {
        if (mMovie == null) {
            finish();
            return;
        }

        Picasso.with(this)
                .load(MovieUtils.buildBackdropUri(this, mMovie))
                .into(mBinding.backdropImageView);
    }

    private void setupTabLayoutIcons() {
        for (int i = 0; i < mBinding.detailMovieTabLayout.getTabCount(); i++) {
            final TabLayout.Tab tab = mBinding.detailMovieTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(ICON_TABS[i]);
            }
        }
    }

    private void shareMovie() {
        Fragment fragment = mPagerAdapter.getItem(DetailMoviePagerAdapter.FRAGMENT_INDEX_VIDEO);
        if (fragment instanceof DetailMovieVideoFragment) {
            DetailMovieVideoFragment videoFragment = (DetailMovieVideoFragment) fragment;

            Video video = videoFragment.getFirstVideo();
            if (video == null) {
                showSnackMessage(R.string.share_video_error_video_null);
                return;
            }

            Uri uri = YoutubeUtils.getUrlVideo(video.getKey());
            Intent intent = ShareCompat.IntentBuilder.from(this)
                    .setType(getString(R.string.share_video_myme_type))
                    .setText(getString(R.string.share_video_message,
                            mMovie.getTitle(),
                            uri.toString()))
                    .getIntent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            }

            startActivity(intent);
            return;
        }

        showSnackMessage(R.string.share_video_error_generic_message);
    }

    private void addMovieToFavorites() {
        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,
                MovieMapper.convertMovieToContentValues(mMovie));
        String key = uri != null ? uri.getLastPathSegment() : "";
        if (TextUtils.isDigitsOnly(key) && (Long.valueOf(key) > 0)) {
            showSnackMessage(R.string.detail_movie_add_favorite_success);
        } else {
            showSnackMessage(R.string.detail_movie_add_favorite_error);
        }
    }

    private void removeMovieToFavorites() {
        if (getContentResolver().delete(ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, mMovie.getId()),
                null, null) > 0) {
            showSnackMessage(R.string.detail_movie_remove_favorite_success);
        } else {
            showSnackMessage(R.string.detail_movie_remove_favorite_error);
        }
    }

    private void startLoader() {
        final Bundle args = new Bundle();
        args.putLong(KEY_MOVIE_ID, mMovie.getMovieId());

        getSupportLoaderManager().initLoader(LOADER_ID, args, this);
    }
    //endregion

    //region LoaderCallbacks Methods
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                CURSOR_FIELDS,
                String.format("(%s = ?)", MovieContract.MovieEntry.COLUMN_MOVIE_ID),
                new String[] { String.valueOf(args.getLong(KEY_MOVIE_ID, 0L)) },
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mMovie == null) {
            return;
        }

        final long id;
        if ((data != null) && (data.moveToFirst())) {
            id = data.getLong(data.getColumnIndex(MovieContract.MovieEntry._ID));
        } else {
            id = 0L;
        }
        mMovie.setId(id);

        invalidateOptionsMenu();

        final Intent intent = new Intent(ListMovieActivity.ACTION_REFRESH_LIST_MOVIE);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    //endregion
}
