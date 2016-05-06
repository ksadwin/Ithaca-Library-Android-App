package edu.ithaca.iclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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

        populateResultDetailView();

        Button removeMat = (Button) findViewById(R.id.remButton);
        removeMat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                matMaker.remove(ScrollingActivity.getCurrBook());
                Toast.makeText(DetailActivity.this,"This item has been removed from Favorites",Toast.LENGTH_LONG);
                Intent intent = new Intent(DetailActivity.this, ScrollingActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
            }
        });

    }

    /**
     * Populates the detail activity for a selected(touched) results
     */
    private void populateResultDetailView(){

            // Find the Book to work with.
            Material currentBook = ScrollingActivity.getCurrBook();

            // Fill the view with a book cover
            ImageView imageView = (ImageView)findViewById(R.id.BookImage);
            //FIXME: every book will have a default image of the IC logo
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView titleText = (TextView)findViewById(R.id.book_Title);
            titleText.setText(currentBook.getBibText2());

            // Author:
            TextView authorText = (TextView) findViewById(R.id.book_Author);
            authorText.setText("" + currentBook.getBibText1());

            // Status:
            TextView statusText = (TextView) findViewById(R.id.Status);
            statusText.setText(Integer.toString(currentBook.getItemStatusCode()));

            //ISBN
            TextView isbn = (TextView)findViewById(R.id.book_ISBN);
            statusText.setText(currentBook.getIsbn());

        }

    }


