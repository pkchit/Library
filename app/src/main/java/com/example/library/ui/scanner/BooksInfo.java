package com.example.library.ui.scanner;

import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;

public class BooksInfo {


    private String name;
    private String author;
    private String downloadImage;
    private String description;
    private String rating;
    private String owner;


    public BooksInfo(JSONArray itemsArray, FirebaseUser user)
    {
        try {
            name = itemsArray.getJSONObject(0).getJSONObject("volumeInfo").getString("title");
            JSONArray authorArray = itemsArray.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors");

            StringBuilder authorsStringBuild = new StringBuilder();

            for(int j=0; j<authorArray.length(); j++){
                authorsStringBuild.append(authorArray.getString(j));
                authorsStringBuild.append(", ");
            }

            author = authorsStringBuild.toString();

            description= itemsArray.getJSONObject(0).getJSONObject("volumeInfo").getString("description");
            rating = itemsArray.getJSONObject(0).getJSONObject("volumeInfo").getString("averageRating");
            downloadImage = itemsArray.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
            downloadImage = downloadImage.replace("http:","https:");

            owner = user.getEmail().replace(".","=*=");

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDownloadImage() {
        return downloadImage;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getOwner() {
        return owner;
    }
}
