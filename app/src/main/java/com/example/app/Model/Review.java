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

    public Integer getRating() { return this.rating; }
    public String getName() { return this.name; }
    public String getMessage() { return this.message; }
    public String getVersion() { return this.version; }
    public String getTitle() { return this.title; }
}
