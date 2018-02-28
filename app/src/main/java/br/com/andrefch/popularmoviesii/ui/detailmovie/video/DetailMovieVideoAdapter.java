package br.com.andrefch.popularmoviesii.ui.detailmovie.video;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.andrefch.popularmoviesii.R;
import br.com.andrefch.popularmoviesii.data.model.Video;
import br.com.andrefch.popularmoviesii.databinding.ItemVideoMovieDetailBinding;

/**
 * Author: andrech
 * Date: 16/02/18
 */

class DetailMovieVideoAdapter extends RecyclerView.Adapter<DetailMovieVideoAdapter.DetailMovieVideoViewHolder> {

    private List<Video> mVideos;
    private final OnVideoSelectedListener mListener;

    DetailMovieVideoAdapter(OnVideoSelectedListener listener) {
        super();
        mListener = listener;
    }

    @Override
    public DetailMovieVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemVideoMovieDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_video_movie_detail,
                parent,
                false);
        return new DetailMovieVideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DetailMovieVideoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.size() : 0;
    }

    private Video getItem(int position) {
        if ((mVideos == null) || (position < 0) || (position >= getItemCount())) {
            return null;
        }
        return mVideos.get(position);
    }

    List<Video> getVideos() {
        return mVideos;
    }

    void setVideos(List<Video> videos) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DetailMovieVideoDiffUtilCallback(mVideos, videos));
        mVideos = videos;
        result.dispatchUpdatesTo(this);
    }

    class DetailMovieVideoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final ItemVideoMovieDetailBinding mBinding;

        DetailMovieVideoViewHolder(ItemVideoMovieDetailBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        void bind(Video video) {
            if (video != null) {
                mBinding.titleTextView.setText(video.getName());
                mBinding.subTitleTextView.setText(video.getType());
            } else {
                mBinding.titleTextView.setText("");
                mBinding.subTitleTextView.setText("");
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            mListener.onVideoSelected(getItem(getAdapterPosition()));
        }
    }

    interface OnVideoSelectedListener {
        void onVideoSelected(Video video);
    }
}
