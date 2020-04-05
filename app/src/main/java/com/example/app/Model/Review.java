package com.example.app.Model;

import java.io.Serializable;

public class Review implements Serializable {

    private Integer rating;
    private String name;
    private String message;
    private String version;
    private String title;

    public Review(Integer rating, String name, String message, String version, String title) {
        this.name = name;
        this.rating = rating;
        this.message = message;
        this.version = version;
        this.title = title;
    }

    public String getRating() { return this.rating + " star rating"; }
    public String getName() { return this.name; }
    public String getMessage() { return this.message; }
    public String getVersion() { return "Version: " + this.version; }
    public String getTitle() { return this.title; }
}
