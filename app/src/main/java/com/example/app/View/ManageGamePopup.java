package com.example.app.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageGamePopup extends LinearLayout {

    private EditText nameView, versionView, genreView, imageUrlView;
    private String type;
    private Game game;
    private PopupWindow popupWindow;
    private View customView;

    public ManageGamePopup(Context context, String type) {
        super(context);
        this.type = type;
        init();
    }

    public ManageGamePopup(Context context, String type, Game game) {
        super(context);
        this.type = type;
        this.game = game;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.manage_game, this);

        nameView = customView.findViewById(R.id.name);
        versionView = customView.findViewById(R.id.version);
        genreView = customView.findViewById(R.id.genre);
        imageUrlView = customView.findViewById(R.id.imageUrl);

        if (this.type.equals("EDIT")) {
            nameView.setText(game.getName());
            versionView.setText(game.getVersion());
            genreView.setText(game.getGenres());
            imageUrlView.setText(game.getImageUrl());
        }
    }

    public void submitHandler() {
        String name = nameView.getText().toString();
        String version = versionView.getText().toString();
        String genre = genreView.getText().toString();
        String imageUrl = imageUrlView.getText().toString();
        List<Review> reviews;
        List<Integer> ratings;

        switch (this.type) {
            case "ADD":
                reviews = new ArrayList<>();
                ratings = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

                Game newGame = new Game(name, version, genre, reviews, ratings, imageUrl);
                GameAdmin.addGame(newGame);
                break;
            case "EDIT":
                reviews = this.game.getReviews();
                ratings = this.game.getRatings();

                Game editedGame = new Game(name, version, genre, reviews, ratings, imageUrl);
                GameAdmin.editGame(editedGame, game);
                break;
        }
    }

    public void setPopupWindow(PopupWindow p) { this.popupWindow = p; }

    public View getView() { return customView; }
}
