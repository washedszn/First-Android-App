package com.example.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.Model.Review;
import com.example.app.R;

import java.util.List;

public class ReviewArrayAdapter extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private List<Review> reviewList;

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

        TextView titleView = convertView.findViewById(R.id.reviewTitle);
        TextView versionView = convertView.findViewById(R.id.reviewVersion);
        TextView nameView = convertView.findViewById(R.id.reviewName);
        TextView ratingView = convertView.findViewById(R.id.reviewRating);
        TextView messageView = convertView.findViewById(R.id.reviewMessage);

        Review review = reviewList.get(position);

        titleView.setText(review.getTitle());
        versionView.setText(review.getVersion());
        nameView.setText(review.getName());
        ratingView.setText(review.getRating() + " star rating");
        messageView.setText(review.getMessage());

        return convertView;
    }
}
