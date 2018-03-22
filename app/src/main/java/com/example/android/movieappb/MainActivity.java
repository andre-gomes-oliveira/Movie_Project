package com.example.android.movieappb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.example.android.movieappb.R.menu.menu_main;

public class MainActivity extends AppCompatActivity {

    public String URL_DATA = "https://api.themoviedb.org/3/movie/popular?api_key=38eeef9aa65a725363ccb5cde9df6342&language=en-US&page=1";
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private List<MovieList> movieList;
    private Menu mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //XML Menu Item
        mMenuItem = findViewById(R.id.menu_main);

        // XML recycler view
        mRecyclerView = findViewById(R.id.recycler_view);

        // About the meansure of the content that will be displayed on the view´s
        mRecyclerView.setHasFixedSize(true);

        // GridLayoutManager call
        int numbeOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numbeOfColumns);
        mRecyclerView.setLayoutManager(layoutManager);

        //ListArray to display on RecyclerView
        movieList = new ArrayList<>();

        loadRecyclerView();
    }

    //Catch Data from a URL and passing into our MovieList
    //To implement the Volley I used this tutorial: https://www.youtube.com/playlist?list=PLk7v1Z2rk4hjHrGKo9GqOtLs1e2bglHHA
    private void loadRecyclerView(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();

        //Volley Request
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, (URL_DATA), new Response.Listener<String>() {

            @Override
            public void onResponse(String string) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray array = jsonObject.getJSONArray("results");

                    for(int i = 0; i <= array.length() - 1; i++){
                        JSONObject json = array.getJSONObject(i);
                        MovieList item = new MovieList(json.getString("title"),
                                json.getString("vote_average"),
                                json.getString("overview"),
                                json.getString("poster_path"),
                                json.getString("release_date"));
                        movieList.add(item);


                    }

                    mAdapter = new Adapter(movieList, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        //Internet Connection Evaluation
                        String msg = null;
                        if(volleyError instanceof NetworkError){
                            msg = getString(R.string.no_connection);
                        } else if(volleyError instanceof ServerError){
                            msg = getString(R.string.no_server);
                        } else if (volleyError instanceof AuthFailureError){
                            msg = getString(R.string.no_authorization);
                        } else if(volleyError instanceof ParseError){
                            msg = getString(R.string.parse_error);
                        } else if(volleyError instanceof NoConnectionError){
                            msg = getString(R.string.no_connection);
                        } else if(volleyError instanceof TimeoutError){
                            msg = getString(R.string.time_out);
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        /*
                        //calling method to display connection if is offline
                        isConnect();
                        */
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*
    //Internet Connection validation
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
    //Connection display
    public void isConnect(){
        if(isOnline()){
            Toast.makeText(getApplicationContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }
    */

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Menu Options
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemSelecionado = item.getItemId();

        if(itemSelecionado == R.id.most_popular){
            movieList.clear();
            URL_DATA = "https://api.themoviedb.org/3/movie/popular?api_key=38eeef9aa65a725363ccb5cde9df6342&language=en-US&page=1";
            loadRecyclerView();
            Toast.makeText(getApplicationContext(),getString(R.string.popular), Toast.LENGTH_SHORT).show();
            return true;

        } else if(itemSelecionado == R.id.rate_view) {
            movieList.clear();
            URL_DATA = "https://api.themoviedb.org/3/movie/top_rated?api_key=38eeef9aa65a725363ccb5cde9df6342&language=en-US&page=1";
            loadRecyclerView();
            Toast.makeText(getApplicationContext(), getString(R.string.rated), Toast.LENGTH_SHORT).show();
            return true;

        } else{
         return super.onOptionsItemSelected(item);
        }
    }
}
