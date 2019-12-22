package com.example.library;

import android.location.Location;

public class Contribution {
    private String id;
    private String name;
    private String category;
    private String author;
    private String description;
    private String owner;
    private String downloadImage;
    private Location address;

    public Contribution() {
    }

    public Contribution(String id, String name, String category, String author, String description, String owner, String downloadImage, Location address) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.author = author;
        this.description = description;
        this.owner = owner;
        this.downloadImage = downloadImage;
        this.address=address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(String downloadImage) {
        this.downloadImage = downloadImage;
    }
}
