package br.com.andrefch.popularmoviesii.ui.detailmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import br.com.andrefch.popularmoviesii.data.model.Movie;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public abstract class BaseDetailMovieFragment extends Fragment {

    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    protected Movie mMovie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMovie(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE, mMovie);
    }

    protected Bundle generateArguments(Movie movie) {
        final Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);
        return args;
    }

    private void loadMovie(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }

        Bundle args;
        if ((mMovie == null) && ((args = getArguments()) != null)) {
            mMovie = args.getParcelable(EXTRA_MOVIE);
        }
    }

    protected DetailMovieActivity getDetailMovieActivity() {
        if (getActivity() instanceof DetailMovieActivity) {
            return (DetailMovieActivity) getActivity();
        }

        return null;
    }
}
