package com.example.app.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import androidx.annotation.Nullable;

import com.example.app.R;

public class RatingView extends LinearLayout {
    private RatingBar ratingBar;
    private ProgressBar progressBar;

    public RatingView(Context context) {
        super(context);
        init();
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rating_view, this);

        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
    }

    public void setRating(int rating, int progress) {
        this.ratingBar.setRating(rating);

        this.progressBar.setProgress(0);
        this.progressBar.setProgress(100);
        this.progressBar.setProgress(progress);
    }
}
