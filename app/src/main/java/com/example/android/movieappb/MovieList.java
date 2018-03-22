package com.example.android.movieappb;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;


public class MovieList implements Parcelable {


    String mTitle;
    String mUserRate;
    String mSinopsis;
    String imageUrl;
    String mData;
    String urlInt = "http://image.tmdb.org/t/p/w185/";


    public String getmTitle() {
        return mTitle;
    }

    public String getmUserRate() {
        return mUserRate;
    }

    public String getmSinopsis() {
        return mSinopsis;
    }

    public String getImageUrl(){
        return (urlInt + imageUrl);
    }

    public String getmData() {
        return mData;
    }

    public MovieList(String mTitle, String mUserRate, String mSinopsis, String imageUrl, String mData){
        this.mTitle = mTitle;
        this.mUserRate = mUserRate;
        this.mSinopsis = mSinopsis;
        this.imageUrl = imageUrl;
        this.mData = mData;
    }

    protected MovieList(Parcel in) {
        mTitle = in.readString();
        mUserRate = in.readString();
        mSinopsis = in.readString();
        imageUrl = in.readString();
        mData = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mUserRate);
        dest.writeString(mSinopsis);
        dest.writeString(imageUrl);
        dest.writeString(mData);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieList> CREATOR = new Parcelable.Creator<MovieList>() {
        @Override
        public MovieList createFromParcel(Parcel in) {
            return new MovieList(in);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };
}
