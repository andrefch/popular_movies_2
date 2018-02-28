package br.com.andrefch.popularmoviesii.ui.listmovie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Movie;
import br.com.andrefch.popularmoviesii.databinding.ItemListMovieBinding;
import br.com.andrefch.popularmoviesii.utilities.MovieUtils;

/**
 * Author: andrech
 * Date: 05/02/18
 */

class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListMovieViewHolder> {

    private List<Movie> mMovies;
    private final OnMovieSelectedListener mListener;

    ListMovieAdapter(OnMovieSelectedListener listener) {
        super();
        mListener = listener;
    }

    @Override
    public ListMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemListMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_list_movie,
                parent,
                false);

        return new ListMovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ListMovieViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    private Movie getItem(int position) {
        if ((position < 0) || (position >= getItemCount())) {
            return null;
        }

        return mMovies.get(position);
    }

    void setMovies(List<Movie> movies) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ListMovieDiffUtilCallback(mMovies, movies));
        mMovies = movies;
        result.dispatchUpdatesTo(this);
    }

    class ListMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemListMovieBinding mBinding;

        ListMovieViewHolder(ItemListMovieBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(Movie movie) {
            if (movie != null) {
                final Context context = getContext();
                Picasso.with(context)
                        .load(MovieUtils.buildPosterUri(context, movie))
                        .into(mBinding.posterMovieImageView);
            } else {
                mBinding.posterMovieImageView.setImageBitmap(null);
            }
        }

        private Context getContext() {
            return this.itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }

            final int position = getAdapterPosition();
            mListener.onMovieSelected(getItem(position));
        }
    }

    interface OnMovieSelectedListener {
        void onMovieSelected(Movie movie);
    }
}
