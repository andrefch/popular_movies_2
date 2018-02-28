package br.com.andrefch.popularmoviesii.ui.detailmovie.info;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.databinding.FragmentDetailMovieInfoBinding;
import br.com.andrefch.popularmoviesii.ui.detailmovie.BaseDetailMovieFragment;
import br.com.andrefch.popularmoviesii.utilities.MovieUtils;

public class DetailMovieInfoFragment extends BaseDetailMovieFragment {

    private FragmentDetailMovieInfoBinding mBinding;

    public static DetailMovieInfoFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("Movie cannot be null.");
        }

        DetailMovieInfoFragment fragment = new DetailMovieInfoFragment();
        fragment.setArguments(fragment.generateArguments(movie));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_movie_info, container, false);
        showMovie();
        return mBinding.getRoot();
    }

    private void showMovie() {
        Picasso.with(getContext())
                .load(MovieUtils.buildPosterUri(getContext(), mMovie))
                .into(mBinding.posterImageView);
        mBinding.titleTextView.setText(mMovie.getTitle());
        mBinding.originalTitleTextView.setText(getString(R.string.format_original_title_movie,
                mMovie.getOriginalTitle()));
        mBinding.releaseDateTextView.setText(MovieUtils.getReleaseDate(getContext(), mMovie));
        mBinding.averageVoteTextView.setText(getString(R.string.format_average_vote_movie, mMovie.getVoteAverage()));
        mBinding.overviewTextView.setText(mMovie.getOverview());
    }
}
