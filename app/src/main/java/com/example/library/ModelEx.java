package com.example.library;

public class ModelEx {
    public String mId;
    public String mTitle;
    public String mauthor;
    public String mImageURL;
    public String owner;



    public String mdescription;
    public ModelEx() {

    }

    public ModelEx(String mId, String mTitle,String owner, String mauthor, String mImageURL, String mdescription) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.owner=owner;
        this.mauthor = mauthor;
        this.mImageURL= mImageURL;
        this.mdescription=mdescription;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmId() {
        return mId;
    }

    public String getMdescription() {
        return mdescription;
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
