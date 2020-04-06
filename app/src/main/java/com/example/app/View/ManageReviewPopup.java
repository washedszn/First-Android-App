package com.example.app.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.app.Model.Game;
import com.example.app.Model.Review;
import com.example.app.R;

public class ManageReviewPopup extends LinearLayout {
    private EditText nameView, titleView, ratingView, messageView;
    private Button submit, cancel;
    private String type;
    private Game game;
    private Review review;
    private PopupWindow popupWindow;
    private static View customView;

    public ManageReviewPopup(Context context, String type, Game game) {
        super(context);
        this.type = type;
        this.game = game;
        init();
    }

    public ManageReviewPopup(Context context, String type, Game game, Review review) {
        super(context);
        this.type = type;
        this.game = game;
        this.review = review;
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.manage_review, this);

        nameView = customView.findViewById(R.id.name);
        titleView = customView.findViewById(R.id.title);
        ratingView = customView.findViewById(R.id.rating);
        messageView = customView.findViewById(R.id.message);

        submit = customView.findViewById(R.id.submit);
        cancel = customView.findViewById(R.id.cancel);

        if (this.type.equals("EDIT")) {
            nameView.setText(review.getName());
            titleView.setText(review.getTitle());
            ratingView.setText(review.getRating() + "");
            messageView.setText(review.getMessage());
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitHandler();
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void submitHandler() {
        String name = nameView.getText().toString();
        String title = titleView.getText().toString();
        String message = messageView.getText().toString();
        int rating = Integer.parseInt(ratingView.getText().toString());

        switch (this.type) {
            case "ADD":
                Review newReview = new Review(rating, name, message, game.getVersion(), title);
                game.addReview(newReview);
                break;
            case "EDIT":
                Review editedReview = new Review(rating, name, message, game.getVersion(), title);
                game.editReview(editedReview, review);
                break;
        }
    }

    public void setPopupWindow(PopupWindow p) { this.popupWindow = p; }

    public static View getView() { return customView; }
}
