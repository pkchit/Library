package com.example.library;

public class Books {
    private String name, description, category, author, email, image, Bid, date,time;

    public Books(String name, String description, String category, String author, String email, String image, String bid, String date, String time) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.author = author;
        this.email = email;
        this.image = image;
        Bid = bid;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
