package com.app.locxshop.activities;

public class User {
    String name, email, bio, imageurl;

    public User(String name, String email, String bio, String imageurl) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.imageurl = imageurl;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
