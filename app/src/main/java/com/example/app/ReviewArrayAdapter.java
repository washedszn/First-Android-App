package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.Model.Review;

import java.util.List;

public class ReviewArrayAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<Review> reviewList;
    private TextView titleView;
    private TextView versionView;
    private TextView nameView;
    private TextView ratingView;
    private TextView messageView;

    public ReviewArrayAdapter(Context context, List objects) {
        super (context, R.layout.review_list_view, objects);
        layoutInflater = LayoutInflater.from(context);
        reviewList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.review_list_view, parent, false);
        }

        titleView = convertView.findViewById(R.id.reviewTitle);
        versionView = convertView.findViewById(R.id.reviewVersion);
        nameView = convertView.findViewById(R.id.reviewName);
        ratingView = convertView.findViewById(R.id.reviewRating);
        messageView = convertView.findViewById(R.id.reviewMessage);

        Review review = reviewList.get(position);

        titleView.setText(review.getTitle());
        versionView.setText(review.getVersion());
        nameView.setText(review.getName());
        ratingView.setText(review.getRating());
        messageView.setText(review.getMessage());

        return convertView;
    }
}
