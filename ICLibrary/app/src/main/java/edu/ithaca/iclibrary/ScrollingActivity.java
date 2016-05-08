package edu.ithaca.iclibrary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

/**
 * Displays the first 25 results of a search query to the IC Library. Queries are fed from
 * MainActivity. Results are displayed as clickable units in a ListView.
 */
public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "ScrollingActivity";
    //public static final String book_ID = "book_ID";
    private List<Material> myBooks = new ArrayList<>();
    private ArrayAdapter<Material> adapter;
    private DatabaseRequest req = new DatabaseRequest();
    private MaterialCoder matMaker = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matMaker = new MaterialCoder(getApplicationContext());

        //Show loading message
        Toast.makeText(ScrollingActivity.this, "Searching...", Toast.LENGTH_LONG).show();

        //Get the query terms from MainActivity Intent
        String[] queryTerms = getIntent().getExtras().getStringArray("query_terms");

        //Make database request using these terms
        try {
            URL url = XMLParser.makeURL(queryTerms[1], queryTerms[0]);
            req.execute(url);
        } catch (MalformedURLException | URISyntaxException e) {
            Log.w(TAG, e.toString());
            Toast.makeText(ScrollingActivity.this, "No results found.", Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_scroll);


        //TODO: work on this to save results on the stack using the savedResultsStorage
        //Are we still doing this ?
        Button savebutton = (Button) findViewById(R.id.savebutton);
        assert savebutton != null;
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    matMaker.saveMats(myBooks);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(ScrollingActivity.this, "Current search results have been saved!!",
                        Toast.LENGTH_LONG).show();
            }
        });


        // Favorites button to display favorited books
        Button favs = (Button) findViewById(R.id.favButton);
        assert favs != null;
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resDet = new Intent(ScrollingActivity.this, FavoriteBooks.class);
                startActivity(resDet);

            }
        });
    }



    private void populateListView() {
        if (myBooks.size() > 0) {
            //initialize adapter
            adapter = new MyListAdapter();

            //configure the list view
            ListView list = (ListView) findViewById(R.id.bookListView);
            list.setAdapter(adapter);
        } else {
            Toast.makeText(ScrollingActivity.this, "No results found.", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Custom ArrayAdapter to populate ListView.
     */
    private class MyListAdapter extends ArrayAdapter<Material> {

        public MyListAdapter() {
            super(ScrollingActivity.this, R.layout.book_view, myBooks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // This will make sure there is a view to work with (may be null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.book_view, parent, false);
            }

            // Find the Book to work with.
            final Material currentBook = myBooks.get(position);


            // Fill the view with a book cover
            ImageView imageView = (ImageView) itemView.findViewById(R.id.bookCover);
            //FIXME: every book will have a default image of the IC logo
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView titleText = (TextView) itemView.findViewById(R.id.booktxt_Title);
            titleText.setText(currentBook.getBibText1());

            // Author:
            TextView authorText = (TextView) itemView.findViewById(R.id.booktxt_Author);
            authorText.setText(currentBook.getBibText2());

            // Status:
            TextView statusText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(currentBook.translateItemStatusCode());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeDetailActivity(currentBook);
                }
            });
            return itemView;
        }

    }
    /**
     * Creates FavoriteDetailActivity for selected Material.
     * @param book clicked Material
     */
    private void makeDetailActivity(Material book) {
        Intent i = new Intent(this, DetailActivity.class);
        String json = MaterialCoder.encode(book).toString();
        i.putExtra("book", json);
        startActivity(i);
    }

    /**
     * Class that makes API requests of the IC Library asynchronously.
     *
     * @author KSADWIN
     *         3/27/2016
     */
    public class DatabaseRequest extends AsyncTask<URL, Void, ArrayList<Material>> {

        /**
         * doInBackground is called when the DatabaseRequest object's execute() method is called.
         * Pass URLs to XMLParser to make API request to Ithaca Library and parse XML response.
         *
         * @param params: URLs to load
         * @return array of Material objects created from XML found at URL
         */
        @Override
        protected ArrayList<Material> doInBackground(URL... params) {
            ArrayList<Material> books = new ArrayList<>();
            for (URL u : params) {
                books.addAll(XMLParser.getMaterialsFromLibrary(u));
            }
            return books;
        }

        /**
         * onPostExecute is called when the DatabaseRequest object completes doInBackground().
         * Sets ScrollingActivity's member variable myBooks to contain returned materials,
         * then calls populateListView() to update Activity.
         *
         * @param materials: the resultant ArrayList of Materials returned by doInBackground().
         */
        @Override
        protected void onPostExecute(ArrayList<Material> materials) {
            myBooks = materials;
            populateListView();
        }

    }
}
