package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {

    private Game game;
    private Review review;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    private DrawRatingView drawRatingView;
    private int gamePosition, reviewPosition;

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
        gamePosition = intent.getExtras().getInt("game");
        reviewPosition = intent.getExtras().getInt("review");

        game = GameAdmin.getGame(gamePosition);
        review = game.getReview(reviewPosition);

        drawRatingView.setRating(review.getRating());

        String title = getString(R.string.title) + ": " + review.getTitle();
        String version = getString(R.string.version) + ": " + review.getVersion();
        String name = getString(R.string.name) + ": " + review.getName();
        String message = getString(R.string.message) + ": " + review.getMessage();

        reviewTitle.setText(title);
        reviewVersion.setText(version);
        reviewName.setText(name);
        reviewMessage.setText(message);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);

        String currentLocal = GameAdmin.getLocal() ? "nl" : "en";

        menu.findItem(R.id.menuLocal).setTitle(currentLocal);

        MenuItem hide = menu.findItem(R.id.menuTitle);
        hide.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (GameAdmin.getLocal()) {
            setLocale("nl");
        } else {
            setLocale("en");
        }
        GameAdmin.setLocal();

        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, ReviewActivity.class);
        refresh.putExtra("game", gamePosition);
        refresh.putExtra("review", reviewPosition);
        finish();
        startActivity(refresh);
    }
}
