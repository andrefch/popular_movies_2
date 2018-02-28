package br.com.andrefch.popularmoviesii.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Author: andrech
 * Date: 29/01/18
 */

public class Movie implements Parcelable {

    private long mId;
    private long mMovieId;
    private String mTitle;
    private String mOriginalTitle;
    private String mOverview;
    private long mVoteCount;
    private double mVoteAverage;
    private double mPopularity;
    private String mPosterPath;
    private String mBackdropPath;
    private Date mReleaseDate;

    public Movie() {
        super();
        mId = 0L;
        mMovieId = 0L;
        mVoteCount = 0L;
        mVoteAverage = 0;
        mPopularity = 0;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getMovieId() {
        return mMovieId;
    }

    public void setMovieId(long movieId) {
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(long voteCount) {
        mVoteCount = voteCount;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }

    //region Parcelable Methods
    private Movie(Parcel parcel) {
        this();
        if (parcel != null) {
            mId = parcel.readLong();
            mMovieId = parcel.readLong();
            mTitle = parcel.readString();
            mOriginalTitle = parcel.readString();
            mOverview = parcel.readString();
            mVoteCount = parcel.readLong();
            mVoteAverage = parcel.readDouble();
            mPopularity = parcel.readDouble();
            mPosterPath = parcel.readString();
            mBackdropPath = parcel.readString();
            long releaseDate = parcel.readLong();
            if (releaseDate != Long.MIN_VALUE) {
                mReleaseDate = new Date(releaseDate);
            }
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mId);
        parcel.writeLong(mMovieId);
        parcel.writeString(mTitle);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOverview);
        parcel.writeLong(mVoteCount);
        parcel.writeDouble(mVoteAverage);
        parcel.writeDouble(mPopularity);
        parcel.writeString(mPosterPath);
        parcel.writeString(mBackdropPath);
        parcel.writeLong(mReleaseDate != null ? mReleaseDate.getTime() : Long.MIN_VALUE);
    }
    //endregion
}
