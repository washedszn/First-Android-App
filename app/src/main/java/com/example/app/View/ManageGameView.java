package com.example.app.View;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.ClipboardManager;

import android.view.ActionMode;
import android.widget.Toast;

import com.example.app.MainActivity;
import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageGameView extends LinearLayout {

    private EditText nameView, versionView, genreView, imageUrlView;
    private String type;
    private Game game;
    private View customView;
    private Context context;

    public ManageGameView(Context context, String type) {
        super(context);
        this.type = type;
        this.context = context;
        init();
    }

    public ManageGameView(Context context, String type, Game game) {
        super(context);
        this.type = type;
        this.game = game;
        this.context = context;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.manage_game, this);

        nameView = customView.findViewById(R.id.name);
        versionView = customView.findViewById(R.id.version);
        genreView = customView.findViewById(R.id.genre);
        imageUrlView = customView.findViewById(R.id.imageUrl);

        imageUrlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                imageUrlView.setText(cm.getText());
                Toast.makeText(context, "Pasted from Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(context, "Added " + name + "!", Toast.LENGTH_SHORT).show();
                break;
            case "EDIT":
                reviews = this.game.getReviews();
                ratings = this.game.getRatings();

                Game editedGame = new Game(name, version, genre, reviews, ratings, imageUrl);
                GameAdmin.editGame(editedGame, game);
                Toast.makeText(context, "Edited " + name + "!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public View getView() { return customView; }
}
