package edu.ithaca.iclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

public class ResultActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Favorite button to display favorited results
        Button favs = (Button) findViewById(R.id.favButton);
        /*
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResultActivity.this, "This should take you to the \"Favorites\" Page !!",
                        Toast.LENGTH_LONG).show();

            }
        });
        */

        //get curentbook clicked
        //get info and setcontent on respective layout.
        //add a favorite button?

        populateResultDetailView(getIntent().getExtras());

    }

    /**
     * Populates the detail activity for a selected(touched) results
     */
    private void populateResultDetailView(Bundle extras){

            // Find the Book to work with.
            Material currentBook = ScrollingActivity.currBook;

            // Fill the view with a book cover
            ImageView imageView = (ImageView)findViewById(R.id.BookImage);
            //FIXME: every book will have a default image of the IC logo
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView titleText = (TextView)findViewById(R.id.book_Title);
            titleText.setText(extras.getString("bibtext1"));

            // Author:
            TextView authorText = (TextView) findViewById(R.id.book_Author);
            authorText.setText(extras.getString("bibtext2"));

            // Status:
            TextView statusText = (TextView) findViewById(R.id.Status);
            statusText.setText(extras.getString("status"));

            //ISBN
            TextView isbnText = (TextView)findViewById(R.id.book_ISBN);
            isbnText.setText(extras.getString("isbn"));

        }

    }


