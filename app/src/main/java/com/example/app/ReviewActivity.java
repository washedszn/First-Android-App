package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.View.DrawRatingView;
import com.example.app.View.StarView;
import com.example.app.View.ManageReviewPopup;

public class ReviewActivity extends AppCompatActivity {

    private Game game;
    private Review review;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    private DrawRatingView drawRatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView reviewTitle = findViewById(R.id.reviewTitle);
        TextView reviewVersion = findViewById(R.id.reviewVersion);
        TextView reviewName = findViewById(R.id.reviewName);
        TextView reviewMessage = findViewById(R.id.reviewMessage);
        Button editBtn = findViewById(R.id.editBtn);
        drawRatingView = findViewById(R.id.drawRating);

        Intent intent = getIntent();
        int gamePosition = intent.getExtras().getInt("game");
        final int reviewPosition = intent.getExtras().getInt("review");

        game = GameAdmin.getGame(gamePosition);
        review = game.getReview(reviewPosition);

        drawRatingView.setRating(review.getRating());

        reviewTitle.setText("Title: " + review.getTitle());
        reviewVersion.setText("Version: " + review.getVersion());
        reviewName.setText("Name: " + review.getName());
        reviewMessage.setText("Message: " + review.getMessage());

        constraintLayout = findViewById(R.id.reviewActivity);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ManageReviewPopup manageReviewPopup = new ManageReviewPopup(ReviewActivity.this, "EDIT", game, review);

                        popupWindow = new PopupWindow(manageReviewPopup.getView(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        manageReviewPopup.setPopupWindow(popupWindow);
                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
                    }
                }
        );
    }
}
