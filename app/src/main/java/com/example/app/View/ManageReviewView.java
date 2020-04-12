package com.example.app.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.R;

public class ManageReviewView extends LinearLayout {
    private EditText nameView, titleView, ratingView, messageView;
    private String type;
    private Game game;
    private Review review;
    private View customView;
    private Context context;

    public ManageReviewView(Context context, String type, Game game) {
        super(context);
        this.type = type;
        this.game = game;
        this.context = context;
        init();
    }

    public ManageReviewView(Context context, String type, Game game, Review review) {
        super(context);
        this.type = type;
        this.game = game;
        this.review = review;
        this.context = context;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.manage_review, this);

        nameView = customView.findViewById(R.id.name);
        titleView = customView.findViewById(R.id.title);
        ratingView = customView.findViewById(R.id.rating);
        messageView = customView.findViewById(R.id.message);

        if (this.type.equals("EDIT")) {
            String rating = "" + review.getRating();

            nameView.setText(review.getName());
            titleView.setText(review.getTitle());
            ratingView.setText(rating);
            messageView.setText(review.getMessage());
        }
    }

    public void submitHandler() {
        String name = nameView.getText().toString();
        String title = titleView.getText().toString();
        String message = messageView.getText().toString();
        int rating = Integer.parseInt(ratingView.getText().toString());

        rating = rating > 5 ? 5 : rating;
        rating = rating < 1 ? 1 : rating;

        switch (this.type) {
            case "ADD":
                Review newReview = new Review(rating, name, message, game.getVersion(), title);
                game.addReview(newReview);
                Toast.makeText(context, "Added new Review!", Toast.LENGTH_SHORT).show();
                break;
            case "EDIT":
                Review editedReview = new Review(rating, name, message, game.getVersion(), title);
                game.editReview(editedReview, review);
                Toast.makeText(context, "Edited Review!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public View getView() { return customView; }
}
