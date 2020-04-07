package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.app.Adapter.ReviewArrayAdapter;
import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.View.ManageGameView;
import com.example.app.View.ManageReviewView;
import com.example.app.View.RatingView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private ManageReviewView manageReviewView;
    private ManageGameView manageGameView;
    private ConstraintLayout constraintLayout;
    private Button submitRatingBtn;
    private Button cancelRatingBtn;
    private RatingView star1, star2, star3, star4, star5;
    private TextView ratingView;

    private ReviewArrayAdapter adapter;
    private Integer newRating;
    private Game game;
    private int gamePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.e("test", "fired");

        ListView reviewListView = findViewById(R.id.reviewListView);

        ImageView imageView = findViewById(R.id.gameImage);
        TextView nameView = findViewById(R.id.gameName);
        TextView versionView = findViewById(R.id.gameVersion);
        TextView genresView = findViewById(R.id.gameGenre);
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

        String version = getString(R.string.version) + ": " + game.getVersion();
        String genre = getString(R.string.genre)+ ": " + game.getGenres();

        versionView.setText(version);
        genresView.setText(genre);

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

        Button addRatingBtn = findViewById(R.id.addRatingBtn);
        Button addReviewBtn = findViewById(R.id.addReviewBtn);
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
                                onResume();
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

                        manageReviewView = new ManageReviewView(GameActivity.this, "ADD", game);

                        popupWindow = new PopupWindow(manageReviewView.getView(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                        manageReviewView.setPopupWindow(popupWindow);
                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                        popupWindow.setFocusable(true);
                        popupWindow.update();

                        View customView = manageReviewView.getView();

                        Button submit = customView.findViewById(R.id.submit);
                        Button cancel = customView.findViewById(R.id.cancel);

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                manageReviewView.submitHandler();

                                Intent refresh = new Intent(GameActivity.this, GameActivity.class);
                                refresh.putExtra("game", gamePosition);
                                finish();
                                startActivity(refresh);

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
                }
        );
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        adapter.notifyDataSetChanged();

        Game updatedGame = GameAdmin.getGame(gamePosition);

        star1.setRating(1, updatedGame.getHistogramPercentage(1));
        star2.setRating(2, updatedGame.getHistogramPercentage(2));
        star3.setRating(3, updatedGame.getHistogramPercentage(3));
        star4.setRating(4, updatedGame.getHistogramPercentage(4));
        star5.setRating(5, updatedGame.getHistogramPercentage(5));

        DecimalFormat df = new DecimalFormat("#.##");
        ratingView.setText(df.format(updatedGame.getAverageRating()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);

        String currentLocal = GameAdmin.getLocal() ? "nl" : "en";

        menu.findItem(R.id.menuLocal).setTitle(currentLocal);
        menu.findItem(R.id.menuTitle).setTitle("Edit Game");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.menuTitle):
                manageGameView = new ManageGameView(GameActivity.this, "EDIT", game);

                popupWindow = new PopupWindow(manageGameView.getView(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                manageGameView.setPopupWindow(popupWindow);
                popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();

                View customView = manageGameView.getView();

                Button submit = customView.findViewById(R.id.submit);
                Button cancel = customView.findViewById(R.id.cancel);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        manageGameView.submitHandler();

                        Intent refresh = new Intent(GameActivity.this, GameActivity.class);
                        refresh.putExtra("game", gamePosition);
                        finish();
                        startActivity(refresh);

                        popupWindow.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                break;
            case (R.id.menuLocal):
                if (GameAdmin.getLocal()) {
                    setLocale("nl");
                } else {
                    setLocale("en");
                }
                GameAdmin.setLocal();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Intent refresh = new Intent(this, GameActivity.class);
        refresh.putExtra("game", gamePosition);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            startActivity(refresh);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            finish();
            startActivity(refresh);
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, GameActivity.class);
        refresh.putExtra("game", gamePosition);
        finish();
        startActivity(refresh);
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
}
