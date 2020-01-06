package com.example.library;

public class ModelEx {
    public String mId, mTitle, mauthor, mImageURL,owner;
    public ModelEx() {

    }

    public ModelEx(String mId, String mTitle,String owner, String mauthor, String mImageURL) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.owner=owner;
        this.mauthor = mauthor;
        this.mImageURL= mImageURL;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmId() {
        return mId;
    }

    public String getMauthor() {
        return mauthor;
    }

    public void setMauthor(String mauthor) {
        this.mauthor = mauthor;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getmauthor() {
        return mauthor;
    }

    public void setmDesc(String mauthor) {
        this.mauthor = mauthor;
    }
}
