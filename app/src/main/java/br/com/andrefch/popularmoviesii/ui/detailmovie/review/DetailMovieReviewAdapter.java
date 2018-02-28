package br.com.andrefch.popularmoviesii.ui.detailmovie.review;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Review;
import br.com.andrefch.popularmoviesii.databinding.ItemReviewMovieDetailBinding;

/**
 * Author: andrech
 * Date: 18/02/18
 */

class DetailMovieReviewAdapter extends RecyclerView.Adapter<DetailMovieReviewAdapter.DetailMovieReviewViewHolder> {

    private List<Review> mReviews;

    @Override
    public DetailMovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemReviewMovieDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_review_movie_detail,
                parent,
                false);
        return new DetailMovieReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DetailMovieReviewViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

    private Review getItem(int position) {
        if ((mReviews == null) || (position < 0) || (position >= getItemCount())) {
            return null;
        }

        return mReviews.get(position);
    }

    public void setReviews(List<Review> reviews) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DetailMovieReviewDiffUtilCallback(mReviews, reviews));
        mReviews = reviews;
        result.dispatchUpdatesTo(this);
    }

    class DetailMovieReviewViewHolder extends RecyclerView.ViewHolder {

        private final ItemReviewMovieDetailBinding mBinding;

        DetailMovieReviewViewHolder(ItemReviewMovieDetailBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(Review review) {
            if (review != null) {
                mBinding.authorTextView.setText(review.getAuthor());
                mBinding.contentTextView.setText(review.getContent());
            } else {
                mBinding.authorTextView.setText("");
                mBinding.contentTextView.setText("");
            }
        }
    }
}
