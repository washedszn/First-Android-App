package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.app.Model.Review;

public class ReviewActivity extends AppCompatActivity {

    private Review review;
    private TextView reviewTitle, reviewVersion, reviewName, reviewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewTitle = findViewById(R.id.reviewTitle);
        reviewVersion = findViewById(R.id.reviewVersion);
        reviewName = findViewById(R.id.reviewName);
        reviewMessage = findViewById(R.id.reviewMessage);

        Intent intent = getIntent();
        review = (Review) intent.getExtras().getSerializable("review");

        reviewTitle.setText("Title: " + review.getTitle());
        reviewVersion.setText("Version: " + review.getVersion());
        reviewName.setText("Name: " + review.getName());
        reviewMessage.setText("Message: " + review.getMessage());
    }
}
