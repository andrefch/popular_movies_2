package br.com.andrefch.popularmoviesii.ui.listmovie;

import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Movie;

/**
 * Author: andrech
 * Date: 05/02/18
 */

class ListMovieDiffUtilCallback extends DiffUtil.Callback {

    private final List<Movie> mOldMovies;
    private final List<Movie> mNewMovies;

    public ListMovieDiffUtilCallback(List<Movie> oldMovies, List<Movie> newMovies) {
        super();
        mOldMovies = oldMovies;
        mNewMovies = newMovies;
    }

    @Override
    public int getOldListSize() {
        return mOldMovies != null ? mOldMovies.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewMovies != null ? mNewMovies.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final Movie oldMovie = mOldMovies.get(oldItemPosition);
        final Movie newMovie = mNewMovies.get(newItemPosition);
        return (oldMovie.getMovieId() == newMovie.getMovieId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Movie oldMovie = mOldMovies.get(oldItemPosition);
        final Movie newMovie = mNewMovies.get(newItemPosition);
        return oldMovie.equals(newMovie);
    }
}
