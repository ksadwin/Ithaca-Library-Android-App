package edu.ithaca.iclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity{
    MaterialCoder matMaker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        matMaker = new MaterialCoder(getApplicationContext());


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
            Material currentBook = ScrollingActivity.getCurrBook();

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


