package br.com.andrefch.popularmoviesii.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: andrech
 * Date: 16/02/18
 */

public class Review implements Parcelable {

    private String mReviewId;
    private String mAuthor;
    private String mContent;
    private String mUrl;

    public Review() {
        super();
    }

    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId(String reviewId) {
        mReviewId = reviewId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Review review = (Review) o;

        return mReviewId.equals(review.mReviewId)
                && (mAuthor != null ? mAuthor.equals(review.mAuthor) : review.mAuthor == null)
                && mUrl.equals(review.mUrl);
    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + (mAuthor != null ? mAuthor.hashCode() : 0);
        result = 31 * result + mUrl.hashCode();
        return result;
    }

    //region Parcelable Methods
    private Review(Parcel parcel) {
        this();
        if (parcel != null) {
            mReviewId = parcel.readString();
            mAuthor = parcel.readString();
            mContent = parcel.readString();
            mUrl = parcel.readString();
        }
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mReviewId);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mUrl);
    }
    //endregion
}
