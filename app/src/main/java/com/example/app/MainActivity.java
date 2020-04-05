package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Model.Game;
import com.example.app.Model.GameAdmin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView gameListView;
    private List<Game> gameList;
    private GameAdmin gameAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //readJsonAsset();

        gameListView = findViewById(R.id.ListView);

        gameAdmin = new GameAdmin(this);

        gameList = gameAdmin.getGames();

        GameArrayAdapter adapter = new GameArrayAdapter(this, gameList);

        gameListView.setAdapter(adapter);

        gameListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("game", gameList.get(position));
                        startActivity(intent);
                    }
        });
    }

//    private void readJsonAsset() {
//        try {
//            InputStream is = this.getAssets().open("games.json");
//
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            String jsonString = new String(buffer, "UTF-8");
//
//            File file = new File(this.getFilesDir(),"game_data.json");
//            FileWriter fileWriter = new FileWriter(file);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(jsonString);
//            bufferedWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
