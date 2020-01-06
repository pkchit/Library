package com.example.library.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestListModel {

   private String bookname;
   private String authorname;
   private String bookID;

   public RequestListModel(String bookname, String authorname, String bookID) {
      this.bookname = bookname;
      this.authorname = authorname;
      this.bookID = bookID;
   }

   public String getBookname() {
      return bookname;
   }

   public void setBookname(String bookname) {
      this.bookname = bookname;
   }

   public String getAuthorname() {
      return authorname;
   }

   public void setAuthorname(String authorname) {
      this.authorname = authorname;
   }

   public String getBookID() {
      return bookID;
   }

   public void setBookID(String bookID) {
      this.bookID = bookID;
   }
}