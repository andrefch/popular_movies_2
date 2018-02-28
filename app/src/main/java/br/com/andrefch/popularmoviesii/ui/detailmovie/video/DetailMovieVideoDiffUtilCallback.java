package br.com.andrefch.popularmoviesii.ui.detailmovie.video;

import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.andrefch.popularmoviesii.data.model.Video;

/**
 * Author: andrech
 * Date: 18/02/18
 */

class DetailMovieVideoDiffUtilCallback extends DiffUtil.Callback {

    private final List<Video> mOldVideos;
    private final List<Video> mNewVideos;

    DetailMovieVideoDiffUtilCallback(List<Video> oldVideos, List<Video> newVideos) {
        super();
        mOldVideos = oldVideos;
        mNewVideos = newVideos;
    }

    @Override
    public int getOldListSize() {
        return mOldVideos != null ? mOldVideos.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewVideos != null ? mNewVideos.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final Video oldVideo = mOldVideos.get(oldItemPosition);
        final Video newVideo = mNewVideos.get(newItemPosition);

        return oldVideo.getVideoId().equals(newVideo.getVideoId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Video oldVideo = mOldVideos.get(oldItemPosition);
        final Video newVideo = mNewVideos.get(newItemPosition);

        return oldVideo.equals(newVideo);
    }
}
