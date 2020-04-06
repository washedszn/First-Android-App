package com.example.app.Model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameAdmin {

    public static ArrayList<Game> games;

    static {
        games = new ArrayList<>();
    }

    public static String readJsonAsset(Context context) throws IOException {
        String jsonString;
        InputStream is = context.getAssets().open("games.json");

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        jsonString = new String(buffer, "UTF-8");

        return jsonString;
    }

    public static ArrayList<Review> formatReviews(JSONArray reviewsArray) throws JSONException {
        ArrayList<Review> reviews = new ArrayList<>();

        for (int x = 0; x < reviewsArray.length(); x++) {
            JSONObject reviewObject = reviewsArray.getJSONObject(x);

            Integer rating = reviewObject.getInt("rating");
            String name = reviewObject.getString("name");
            String message = reviewObject.getString("message");
            String version = reviewObject.getString("version");
            String title = reviewObject.getString("title");

            reviews.add(new Review(rating, name, message, version, title));
        }

        return reviews;
    }

    public static ArrayList<Integer> formatRatings(JSONArray ratingsArray) throws JSONException {
        ArrayList<Integer> formattedRatings = new ArrayList<>();

        for (int i = 0; i < ratingsArray.length(); i++) {
            formattedRatings.add(ratingsArray.getInt(i));
        }

        return formattedRatings;
    }

    public static void initGames(Context context) {
        try {

            JSONArray gamesArray = new JSONArray(readJsonAsset(context));

            for (int i = 0; i < gamesArray.length(); i++) {
                JSONObject gameObject = gamesArray.getJSONObject(i);
                JSONArray ratingsArray = gameObject.getJSONArray("ratings");
                JSONArray reviewsArray = gameObject.getJSONArray("reviews");

                String name = gameObject.getString("name");
                String version = gameObject.getString("version");
                String genres = gameObject.getString("genres");
                String imageUrl = gameObject.getString("imageUrl");

                ArrayList<Review> reviews = formatReviews(reviewsArray);

                ArrayList<Integer> ratings = formatRatings(ratingsArray);

                games.add(new Game(name, version, genres, reviews, ratings, imageUrl));
            }
        } catch(JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGame(int position) { games.remove(position); }

    public static ArrayList<Game> getGames() { return games; }

    public static Game getGame(int position) { return games.get(position); }
}
