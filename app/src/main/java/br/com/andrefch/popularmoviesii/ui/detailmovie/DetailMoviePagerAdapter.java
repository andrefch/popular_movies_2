package br.com.andrefch.popularmoviesii.ui.detailmovie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.ui.detailmovie.info.DetailMovieInfoFragment;
import br.com.andrefch.popularmoviesii.ui.detailmovie.review.DetailMovieReviewFragment;
import br.com.andrefch.popularmoviesii.ui.detailmovie.video.DetailMovieVideoFragment;

/**
 * Author: andrech
 * Date: 15/02/18
 */

class DetailMoviePagerAdapter extends FragmentPagerAdapter {

    static final int FRAGMENT_INDEX_VIDEO = 1;

    private final List<Fragment> mFragments;

    DetailMoviePagerAdapter(FragmentManager fragmentManager, Movie movie) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        setupFragments(movie);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    private void setupFragments(Movie movie) {
        mFragments.add(DetailMovieInfoFragment.newInstance(movie));
        mFragments.add(DetailMovieVideoFragment.newInstance(movie));
        mFragments.add(DetailMovieReviewFragment.newInstance(movie));
    }
}
