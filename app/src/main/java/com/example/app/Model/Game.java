package com.example.app.Model;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class Game implements Serializable {

    private String name;
    private String version;
    private String genres;
    private List<Review> reviews;
    private List<Integer> ratings;
    private String imageUrl;

    public Game(String name, String version, String genres, List<Review> reviews, List<Integer> ratings, String imageUrl) {
        this.name = name;
        this.version = version;
        this.genres = genres;
        this.reviews = reviews;
        this.imageUrl = imageUrl;
        this.ratings = ratings;
    }

    public String getImageUrl() { return this.imageUrl; }

    public String getName() { return this.name; }

    public String getVersion() { return this.version; }

    public String getGenres() { return this.genres; }

    public String getReviewTotal() { return this.reviews.size() + " reviews"; }

    public List<Review> getReviews() { return this.reviews; }

    public double getAverageRating() {
        double sum = 0;
        double totalRatings = 0;

        for (int i = 0; i < this.ratings.size(); i++) {
            sum += this.ratings.get(i) * (i + 1);
            totalRatings += this.ratings.get(i);
        }

        DecimalFormat df = new DecimalFormat("#.#");

        //return df.format(sum / totalRatings) + "";
        return sum / totalRatings;
    }

    public Integer getHistogramPercentage(Integer rating) {
        double test = this.ratings.get(rating - 1);
        double total = getTotalRatings();
        
        return (int) Math.ceil((test/total)*100);
    }

    public void setName(String name) { this.name = name; }

    public void addReview(Integer rating, String name, String message, String version, String title) {
        Review newReview = new Review(rating, name, message, version, title);
        reviews.add(newReview);
    }

    public void changeImage(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getTotalRatings() {
        int sum = 0;

        for (int i = 0; i < this.ratings.size(); i++) {
            sum += this.ratings.get(i);
        }

        return sum;
    }

    public void addRating(Integer rating) {
        Integer value;
        switch(rating) {
            case 1:
                value = this.ratings.get(0);
                value = value + 1;
                this.ratings.set(0, value);
                break;
            case 2:
                value = this.ratings.get(1);
                value = value + 1;
                this.ratings.set(1, value);
                break;
            case 3:
                value = this.ratings.get(2);
                value = value + 1;
                this.ratings.set(2, value);
                break;
            case 4:
                value = this.ratings.get(3);
                value = value + 1;
                this.ratings.set(3, value);
                break;
            case 5:
                value = this.ratings.get(4);
                value = value + 1;
                this.ratings.set(4, value);
                break;
        }

        Log.e("boop", this.ratings.toString());
    }
}
