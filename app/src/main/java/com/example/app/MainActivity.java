package com.example.app;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.app.Adapter.GameArrayAdapter;
import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;
import com.example.app.View.ManageGameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GameArrayAdapter adapter;
    private ConstraintLayout constraintLayout;

    private ManageGameView manageGameView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView gameListView = findViewById(R.id.ListView);
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

//    @Override
//    protected void onResume()
//    {
//        // TODO Auto-generated method stub
//        super.onResume();
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);

        String currentLocal = GameAdmin.getLocal() ? "nl" : "en";

        menu.findItem(R.id.menuLocal).setTitle(currentLocal);
        menu.findItem(R.id.menuTitle).setTitle("Add Game");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.menuTitle):
                manageGameView = new ManageGameView(this, "ADD");

                popupWindow = new PopupWindow(manageGameView.getView(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
                        adapter.notifyDataSetChanged();
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
                    item.setTitle("en");
                } else {
                    setLocale("en");
                    item.setTitle("nl");
                }
                GameAdmin.setLocal();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
}
