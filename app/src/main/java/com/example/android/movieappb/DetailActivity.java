package com.example.android.movieappb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private MovieList movieItems;

    private ImageView mDetailImageView;

    private TextView mTitle;

    private TextView mDate;

    private TextView mUserRate;

    private TextView mSinopsis;

    private String image;

    private String title;

    private String date;

    private String rating;

    private String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mDetailImageView = (ImageView) findViewById(R.id.detail_image_view);
        mTitle = (TextView) findViewById(R.id.title);
        mDate = (TextView) findViewById(R.id.date);
        mUserRate = (TextView) findViewById(R.id.user_view_rate);
        mSinopsis = (TextView) findViewById(R.id.sinopsis);

        Intent displayIntent = getIntent();
        displayIntent.hasExtra("movies");
        movieItems = getIntent().getParcelableExtra("movies");

        image = movieItems.getImageUrl();
        title = movieItems.getmTitle();
        date = movieItems.getmData();
        rating = movieItems.getmUserRate();
        overview = movieItems.getmSinopsis();

        Picasso.with(this)
                .load(image)
                .into(mDetailImageView);

        mTitle.setText(title);
        mDate.setText(date);
        mUserRate.setText(rating);
        mSinopsis.setText(overview);

    }
}
