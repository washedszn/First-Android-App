package com.example.app.Model;

import android.util.Log;

import java.io.Serializable;
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

    public Review getReview(int i) { return this.reviews.get(i); }

    public Integer getReviewTotal() { return this.reviews.size(); }

    public List<Integer> getRatings() { return this.ratings; }

    public List<Review> getReviews() { return this.reviews; }

    public double getAverageRating() {
        double sum = 0;
        double totalRatings = 0;

        for (int i = 0; i < this.ratings.size(); i++) {
            sum += this.ratings.get(i) * (i + 1);
            totalRatings += this.ratings.get(i);
        }

        return sum / totalRatings;
    }

    public Integer getHistogramPercentage(Integer rating) {
        double test = this.ratings.get(rating - 1);
        double total = getTotalRatings();
        
        return (int) Math.ceil((test/total)*100);
    }

    public void setName(String name) { this.name = name; }

    public void deleteReview(int i) {
        int star = this.reviews.get(i).getRating();
        this.ratings.set(star - 1, this.ratings.get(star - 1) - 1);
        this.reviews.remove(i);
    }

    public void addReview(Review newReview) {
        this.reviews.add(newReview);
        int star = newReview.getRating();
        this.ratings.set(star - 1, this.ratings.get(star - 1) + 1);
        Log.e("test", this.ratings.toString());
    }

    public void editReview(Review editedReview, Review oldReview) {
        int position = this.reviews.indexOf(oldReview);
        this.reviews.set(position, editedReview);

        int old = oldReview.getRating();
        int edit = editedReview.getRating();

        if (old != edit) {
            this.ratings.set(old - 1, this.ratings.get(old - 1) - 1);
            this.ratings.set(edit - 1, this.ratings.get(edit - 1) + 1);
        }
    }

    private Integer getTotalRatings() {
        int sum = 0;

        for (int i = 0; i < this.ratings.size(); i++) {
            sum += this.ratings.get(i);
        }

        return sum;
    }

    public void addRating(Integer rating) {
        this.ratings.set(rating - 1, this.ratings.get(rating - 1) + 1);
        Log.e("test", this.ratings.toString());
    }
}
