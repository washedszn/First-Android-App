package com.example.app.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.app.R;

public class DrawRatingView extends LinearLayout {
    private StarView star2;
    private StarView star3;
    private StarView star4;
    private StarView star5;

    public DrawRatingView(Context context) {
        super(context);
        init();
    }

    public DrawRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.draw_rating_view, this);

        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
    }

    public void setRating(int i) {

        String noStar = "#A9A9A9";

        switch (i) {
            case 1:
                star2.setColor(noStar);
                star3.setColor(noStar);
                star4.setColor(noStar);
                star5.setColor(noStar);
                break;
            case 2:
                star3.setColor(noStar);
                star4.setColor(noStar);
                star5.setColor(noStar);
                break;
            case 3:
                star4.setColor(noStar);
                star5.setColor(noStar);
                break;
            case 4:
                star5.setColor(noStar);
                break;
        }
    }
}
