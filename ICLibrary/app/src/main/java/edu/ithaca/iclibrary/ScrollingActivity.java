package edu.ithaca.iclibrary;

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

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "ScrollingActivity";
    private List<Material> myBooks = new ArrayList<>();
    private ArrayAdapter<Material> adapter;
    private DatabaseRequest req = new DatabaseRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the query terms from MainActivity Intent
        String[] queryTerms = getIntent().getExtras().getStringArray("query_terms");
        //Make database request using these terms
        URL url = null;
        try {
            url = XMLParser.makeURL(queryTerms[1], queryTerms[0]);
        } catch (MalformedURLException | URISyntaxException e) {
            Log.e(TAG, e.toString());
        }
        req.execute(url);



        setContentView(R.layout.activity_scroll);

        //populateBookList();
        populateListView();
        registerItemClick();


        /* save button to save search results as history.
        * work on this to save results on the stack using the savedResultsStorage*/
        Button savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollingActivity.this, "Current Search Results have been \"Saved\"!!",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    /*
    private void populateBookList() {
        myBooks.add(new Books("Tank and AVF", "Kelly Sadwin", R.drawable.hp, "Available"));
        myBooks.add(new Books("Tess", "Prof. Noboby", R.drawable.i, "Available"));
        myBooks.add(new Books("Pride", "Robin Stevens", R.drawable.pride, "Checked Out"));
        myBooks.add(new Books("Naked Pictures", "Jon Stewart", R.drawable.jonste, "Checked Out!"));
        myBooks.add(new Books("Letters for Scarlet", "Julie Gardner", R.drawable.p, "Checked Out"));
        myBooks.add(new Books("Adaptive Web Design", "Aaron Gusatfson", R.drawable.web, "Available"));
        myBooks.add(new Books("Book Covers", "Trevor Wheeler" , R.drawable.gg, "Available"));
        myBooks.add(new Books("Passport", "Immigration Office", R.drawable.pp, "Checked Out"));
    }*/

    private void populateListView(){
        //initialize adapter
        adapter = new MyListAdapter();

        //configure the list view
        ListView list = (ListView) findViewById(R.id.bookListView);
        list.setAdapter(adapter);
    }

     private void registerItemClick(){
         ListView list = (ListView) findViewById(R.id.bookListView);
         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View viewClicked,
                                     int position, long id) {

                 Material clickedBook = myBooks.get(position);
                 String message = "You clicked position " + position
                         + " Which is Book Title " + clickedBook.getBibText2();
                 Toast.makeText(ScrollingActivity.this, message, Toast.LENGTH_LONG).show();
             }
         });
     }

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
            Material currentBook = myBooks.get(position);

            // Fill the view with a book cover
            ImageView imageView = (ImageView)itemView.findViewById(R.id.bookCover);
            //FIXME: every book will have a default image of the IC logo
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView titleText = (TextView) itemView.findViewById(R.id.booktxt_Title);
            titleText.setText(currentBook.getBibText2());

            // Author:
            TextView authorText = (TextView) itemView.findViewById(R.id.booktxt_Author);
            authorText.setText("" + currentBook.getBibText1());

            // Status:
            TextView statusText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(Integer.toString(currentBook.getItemStatusCode()));

            return itemView;
        }
    }

    /**
     * Class that makes API requests of the IC Library asynchronously.
     * @author KSADWIN
     * 3/27/2016
     */
    public class DatabaseRequest extends AsyncTask<URL, Void, ArrayList<Material>> {

        /**
         * Make API request to Ithaca Library and parse XML response.
         * @param params: eventually that will contain the URLs to load but presently it does not
         * @return array of Material objects created from XML found at URL
         */
        protected ArrayList<Material> doInBackground(URL... params) {
            ArrayList<Material> books = new ArrayList<>();
            for (URL u : params) {
                books.addAll(XMLParser.getMaterialsFromLibrary(u));
            }
            return books;
        }
        protected void onPostExecute (ArrayList<Material> materials) {
            myBooks = materials;
            populateListView();
        }
    }




    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
