package com.example.admin.amazonproject;

/**
 * Created by Admin on 10/12/2017.
 */

public class BookComplete {
    String Title, Author, ImageURL;

    public BookComplete(String title, String author, String imageURL) {
        Title = title;
        Author = author;
        ImageURL = imageURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
