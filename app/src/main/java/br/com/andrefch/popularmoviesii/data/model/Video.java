package br.com.andrefch.popularmoviesii.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class Video implements Parcelable {

    private String mVideoId;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mType;

    public Video() {
        super();
        mSize = 0;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Video video = (Video) o;

        return mSize == video.mSize && mVideoId.equals(video.mVideoId)
                && mKey.equals(video.mKey) && mName.equals(video.mName)
                && mSite.equals(video.mSite)
                && (mType != null ? mType.equals(video.mType) : video.mType == null);
    }

    @Override
    public int hashCode() {
        int result = mVideoId.hashCode();
        result = 31 * result + mKey.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mSite.hashCode();
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        return result;
    }

    //region Parcelable Methods
    private Video(Parcel parcel) {
        this();
        if (parcel != null) {
            mVideoId = parcel.readString();
            mKey = parcel.readString();
            mName = parcel.readString();
            mSite = parcel.readString();
            mSize = parcel.readInt();
            mType = parcel.readString();
        }
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel parcel) {
            return new Video(parcel);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mVideoId);
        parcel.writeString(mKey);
        parcel.writeString(mName);
        parcel.writeString(mSite);
        parcel.writeInt(mSize);
        parcel.writeString(mType);
    }
    //endregion
}