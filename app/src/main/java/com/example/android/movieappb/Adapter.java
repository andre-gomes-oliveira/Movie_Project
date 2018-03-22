package com.example.android.movieappb;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static android.content.Intent.EXTRA_TEXT;


public class Adapter extends RecyclerView.Adapter<Adapter.NumberViewHolder> {

    private static final String TAG = Adapter.class.getSimpleName();

    private List<MovieList> movieItems;
    private Context context;

    public RecyclerView recyclerView;

    //Adapter Constructor
    public Adapter(List<MovieList> movieItems, Context context) {
        this.movieItems = movieItems;
        this.context = context;
    }

    //ViewHolder
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new NumberViewHolder(view);
    }

    //Data into ViewHolder
    @Override
    public void onBindViewHolder(NumberViewHolder holder, final int position) {

        final MovieList movieList = movieItems.get(position);

        Picasso.with(context)
                .load(movieList.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount(){
        return this.movieItems.size();
    }


    public class NumberViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public FrameLayout frameLayout;

        public NumberViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    MovieList movies = movieItems.get(pos);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movies", movies);
                    context.startActivity(intent);
                }
            });
        }
    }
}
