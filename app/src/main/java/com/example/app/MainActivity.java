package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.Model.Review;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView gameListView;
    private GameArrayAdapter adapter;

    private ConstraintLayout constraintLayout;
    private PopupWindow popupWindow;
    private Button submitGameBtn, cancelGameBtn;
    private EditText addGameName, addGameVersion, addGameGenre, addGameImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameListView = findViewById(R.id.ListView);
        constraintLayout = findViewById(R.id.main_activity);

        GameAdmin.initGames(this);

        adapter = new GameArrayAdapter(this, GameAdmin.getGames());

        gameListView.setAdapter(adapter);

        gameListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("game", position);
                        startActivity(intent);
                    }
        });

        gameListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        GameAdmin.deleteGame(position);
                        adapter.notifyDataSetChanged();
                        return true;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);

        menu.findItem(R.id.menuTitle).setTitle("Add Game");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.add_game, null);

        addGameName = customView.findViewById(R.id.addGameName);
        addGameVersion = customView.findViewById(R.id.addGameVersion);
        addGameGenre = customView.findViewById(R.id.addGameGenre);
        addGameImageUrl = customView.findViewById(R.id.addGameImageUrl);

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
                ArrayList<Integer> ratings = new ArrayList<>();
                ArrayList<Review> reviews = new ArrayList<>();

                ratings.add(0);
                ratings.add(0);
                ratings.add(0);
                ratings.add(0);
                ratings.add(0);

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
