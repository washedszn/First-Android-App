package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;

public class ReviewActivity extends AppCompatActivity {

    private Game game;
    private Review review;

    private TextView reviewTitle, reviewVersion, reviewName, reviewMessage;
    private EditText addReviewName, addReviewTitle, addReviewRating, addReviewMessage;
    private Button editBtn, submitReviewBtn, cancelReviewBtn;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewTitle = findViewById(R.id.reviewTitle);
        reviewVersion = findViewById(R.id.reviewVersion);
        reviewName = findViewById(R.id.reviewName);
        reviewMessage = findViewById(R.id.reviewMessage);
        editBtn = findViewById(R.id.editBtn);

        Intent intent = getIntent();
        int gamePosition = intent.getExtras().getInt("game");
        final int reviewPosition = intent.getExtras().getInt("review");

        game = GameAdmin.getGame(gamePosition);
        review = game.getReview(reviewPosition);

        reviewTitle.setText("Title: " + review.getTitle());
        reviewVersion.setText("Version: " + review.getVersion());
        reviewName.setText("Name: " + review.getName());
        reviewMessage.setText("Message: " + review.getMessage());

        constraintLayout = findViewById(R.id.reviewActivity);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) ReviewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.add_review, null);

                        addReviewName = customView.findViewById(R.id.addReviewName);
                        addReviewTitle = customView.findViewById(R.id.addReviewTitle);
                        addReviewRating = customView.findViewById(R.id.addReviewRating);
                        addReviewMessage = customView.findViewById(R.id.addReviewMessage);

                        addReviewName.setText(review.getName());
                        addReviewTitle.setText(review.getTitle());
                        addReviewRating.setText(review.getRating() + "");
                        addReviewMessage.setText(review.getMessage());

                        cancelReviewBtn = customView.findViewById(R.id.cancelReviewBtn);
                        submitReviewBtn = customView.findViewById(R.id.submitReviewBtn);

                        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                        popupWindow.setFocusable(true);
                        popupWindow.update();

                        submitReviewBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Log.e("test", "fired");

                                String name = addReviewName.getText().toString();
                                String title = addReviewTitle.getText().toString();
                                int rating = Integer.parseInt(addReviewRating.getText().toString());
                                String message = addReviewMessage.getText().toString();

                                game.deleteReview(reviewPosition);
                                game.addReview(rating, name, message, game.getVersion(), title);
                                popupWindow.dismiss();
                            }
                        });

                        cancelReviewBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
        );
    }
}
