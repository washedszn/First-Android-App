package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.app.Model.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GameArrayAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<Game> gameList;
    private TextView nameView;
//    private TextView ratingsView;
    private TextView reviewsView;
    private ImageView imageView;

    private RatingBar ratingsBar;

    public GameArrayAdapter(Context context, List objects) {
        super (context, R.layout.game_list_view, objects);
        layoutInflater = LayoutInflater.from(context);
        gameList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.game_list_view, parent, false);
        }

        nameView = convertView.findViewById(R.id.name);
        reviewsView = convertView.findViewById(R.id.review);
        imageView = convertView.findViewById(R.id.imageView);

        ratingsBar = convertView.findViewById(R.id.rating);

        Game game = gameList.get(position);

        ratingsBar.setRating((float) game.getAverageRating());

        nameView.setText(game.getName());
        reviewsView.setText(game.getReviewTotal());

        Picasso.get().load(game.getImageUrl()).into(imageView);
        imageView.setClipToOutline(true);


        return convertView;
    }
}
