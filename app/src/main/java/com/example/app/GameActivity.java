package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;
import com.example.app.View.ManageGamePopup;
import com.example.app.View.ManageReviewPopup;
import com.example.app.View.RatingView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
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

    private ManageReviewPopup manageReviewPopup;

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

                        ManageReviewPopup manageReviewPopup = new ManageReviewPopup(GameActivity.this, "ADD", game);

                        popupWindow = new PopupWindow(manageReviewPopup.getView(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                        manageReviewPopup.setPopupWindow(popupWindow);
                        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
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

        ManageGamePopup manageGamePopup = new ManageGamePopup(GameActivity.this, "EDIT", game);

        popupWindow = new PopupWindow(manageGamePopup.getView(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        manageGamePopup.setPopupWindow(popupWindow);
        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();

        return super.onOptionsItemSelected(item);
    }
}
