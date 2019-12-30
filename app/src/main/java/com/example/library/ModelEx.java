package com.example.library;

public class ModelEx {
    public String mId, mTitle, mDesc, mImageURL;
    public ModelEx() {

    }

    public ModelEx(String mId, String mTitle, String mDesc, String mImageURL) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mImageURL= mImageURL;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}
