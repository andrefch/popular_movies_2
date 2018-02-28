package br.com.andrefch.popularmoviesii.ui.detailmovie.review;

import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Review;

/**
 * Author: andrech
 * Date: 18/02/18
 */

class DetailMovieReviewDiffUtilCallback extends DiffUtil.Callback {
    
    private final List<Review> mOldReviews;
    private final List<Review> mNewReviews;

    DetailMovieReviewDiffUtilCallback(List<Review> oldReviews, List<Review> newReviews) {
        super();
        mOldReviews = oldReviews;
        mNewReviews = newReviews;
    }

    @Override
    public int getOldListSize() {
        return mOldReviews != null ? mOldReviews.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewReviews != null ? mNewReviews.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final Review oldReview = mOldReviews.get(oldItemPosition);
        final Review newReview = mNewReviews.get(newItemPosition);

        return oldReview.getReviewId().equals(newReview.getReviewId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Review oldReview = mOldReviews.get(oldItemPosition);
        final Review newReview = mNewReviews.get(newItemPosition);

        return oldReview.equals(newReview);
    }
}
