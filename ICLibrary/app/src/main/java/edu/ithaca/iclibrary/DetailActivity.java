package edu.ithaca.iclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Displays DetailActivity for searched Material. Includes a button to add the item to favorites list.
 */
public class DetailActivity extends AppCompatActivity{
    MaterialCoder matMaker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        matMaker = new MaterialCoder(getApplicationContext());

        try {
            populateResultDetailView(getIntent().getExtras());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Populates the detail activity for a selected(touched) results
     * @param extras result of getIntent().getExtras() in onCreate()
     */
    private void populateResultDetailView(Bundle extras) throws JSONException {

            // Find the Book to work with.
            JSONObject json = (JSONObject) new JSONTokener(extras.getString("book")).nextValue();
            final Material currentBook = MaterialCoder.decode(json);

            // Fill the view with a book cover
            ImageView imageView = (ImageView)findViewById(R.id.BookImage);
            //FIXME: every book will have a default image of the IC logo
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView titleText = (TextView)findViewById(R.id.book_Title);
            titleText.setText(currentBook.getBibText1());

            // Author:
            TextView authorText = (TextView) findViewById(R.id.book_Author);
            authorText.setText(currentBook.getBibText2());

            // Status:
            TextView statusText = (TextView) findViewById(R.id.Status);
            statusText.setText(currentBook.translateItemStatusCode());

            //ISBN
            TextView isbnText = (TextView)findViewById(R.id.book_ISBN);
            isbnText.setText(currentBook.getIsbn());

            //Location
            TextView locationText = (TextView) findViewById(R.id.booktxt_Location);
            locationText.setText(currentBook.getLocationName());



            Button save = (Button) findViewById(R.id.oneSaveButton);
            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    try{
                        matMaker.saveMat(currentBook);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Toast.makeText(DetailActivity.this, "This material has been saved to favorites.",
                            Toast.LENGTH_LONG).show();
                }

            });

        }

    }


