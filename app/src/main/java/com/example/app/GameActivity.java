package com.example.app;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.View.RatingView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ListView reviewListView;
    private ImageView imageView;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    private Button addRatingBtn, submitRatingBtn, cancelRatingBtn, addReviewBtn, submitReviewBtn, cancelReviewBtn;
    private TextView nameView, versionView, genresView, ratingView;
    private RatingView star1, star2, star3, star4, star5;
    private EditText addReviewName, addReviewTitle, addReviewRating, addReviewMessage;

    private Button submitGameBtn, cancelGameBtn;
    private EditText addGameName, addGameVersion, addGameGenre, addGameImageUrl;

    private ReviewArrayAdapter adapter;
    private Integer newRating;
    private Game game;
    private int gamePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        reviewListView = findViewById(R.id.reviewListView);

        imageView = findViewById(R.id.gameImage);
        nameView = findViewById(R.id.gameName);
        versionView = findViewById(R.id.gameVersion);
        genresView = findViewById(R.id.gameGenre);
        ratingView = findViewById(R.id.gameRating);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        Intent intent =  getIntent();
        gamePosition = intent.getExtras().getInt("game");

        game = GameAdmin.getGame(gamePosition);

        nameView.setText(game.getName());
        versionView.setText("Version: " + game.getVersion());
        genresView.setText(game.getGenres());

        star1.setRating(1, game.getHistogramPercentage(1));
        star2.setRating(2, game.getHistogramPercentage(2));
        star3.setRating(3, game.getHistogramPercentage(3));
        star4.setRating(4, game.getHistogramPercentage(4));
        star5.setRating(5, game.getHistogramPercentage(5));

        DecimalFormat df = new DecimalFormat("#.##");
        ratingView.setText(df.format(game.getAverageRating()));

        Picasso.get().load(game.getImageUrl()).into(imageView);
        imageView.setClipToOutline(true);

        adapter = new ReviewArrayAdapter(this, game.getReviews());

        reviewListView.setAdapter(adapter);

        reviewListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int reviewPosition, long id) {
                        Intent intent = new Intent(GameActivity.this, ReviewActivity.class);
                        intent.putExtra("review", reviewPosition);
                        intent.putExtra("game", gamePosition);
                        startActivity(intent);
                    }
                }
        );

        reviewListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        game.deleteReview(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        addRatingBtn = findViewById(R.id.addRatingBtn);
        addReviewBtn = findViewById(R.id.addReviewBtn);
        constraintLayout = findViewById(R.id.game_activity);

        addRatingBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflater = (LayoutInflater) GameActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.add_rating, null);

                        cancelRatingBtn = customView.findViewById(R.id.cancelRatingBtn);
                        submitRatingBtn = customView.findViewById(R.id.submitRatingBtn);

                        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);

                        submitRatingBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                game.addRating(newRating);
                                popupWindow.dismiss();
                            }
                        });

                        cancelRatingBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
        });

        addReviewBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflater = (LayoutInflater) GameActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView = layoutInflater.inflate(R.layout.add_review, null);

                        addReviewName = customView.findViewById(R.id.addReviewName);
                        addReviewTitle = customView.findViewById(R.id.addReviewTitle);
                        addReviewRating = customView.findViewById(R.id.addReviewRating);
                        addReviewMessage = customView.findViewById(R.id.addReviewMessage);

                        cancelReviewBtn = customView.findViewById(R.id.cancelReviewBtn);
                        submitReviewBtn = customView.findViewById(R.id.submitReviewBtn);

                        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

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

    public void onRadioBtnClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_1_star:
                if (checked)
                    newRating = 1;
                    break;
            case R.id.radio_2_star:
                if (checked)
                    newRating = 2;
                    break;
            case R.id.radio_3_star:
                if (checked)
                    newRating = 3;
                    break;
            case R.id.radio_4_star:
                if (checked)
                    newRating = 4;
                    break;
            case R.id.radio_5_star:
                if (checked)
                    newRating = 5;
                    break;
        }
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);

        menu.findItem(R.id.menuTitle).setTitle("Edit Game");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater layoutInflater = (LayoutInflater) GameActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.add_game, null);

        addGameName = customView.findViewById(R.id.addGameName);
        addGameVersion = customView.findViewById(R.id.addGameVersion);
        addGameGenre = customView.findViewById(R.id.addGameGenre);
        addGameImageUrl = customView.findViewById(R.id.addGameImageUrl);

        addGameName.setText(game.getName());
        addGameVersion.setText(game.getVersion());
        addGameGenre.setText(game.getGenres());
        addGameImageUrl.setText(game.getImageUrl());

        cancelGameBtn = customView.findViewById(R.id.cancelGameBtn);
        submitGameBtn = customView.findViewById(R.id.submitGameBtn);

        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();

        submitGameBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = addGameName.getText().toString();
                String version =  addGameVersion.getText().toString();
                String genre = addGameGenre.getText().toString();
                String imageUrl = addGameImageUrl.getText().toString();
                List<Integer> ratings = game.getRatings();
                List<Review> reviews = game.getReviews();

                GameAdmin.deleteGame(gamePosition);
                GameAdmin.addGame(new Game(name, version, genre, reviews, ratings, imageUrl));
                popupWindow.dismiss();
            }
        });

        cancelGameBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        return super.onOptionsItemSelected(item);
    }
}
