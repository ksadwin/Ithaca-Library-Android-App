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
import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    private List<Material> myBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Toast.makeText(ScrollingActivity.this, "Current Search Results have been \"Saved\"!!", Toast.LENGTH_LONG);
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
        //Build adapter
        ArrayAdapter<Material> adapter = new MyListAdapter();

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
            //FIXME: fill with default image?
            //imageView.setImageResource(currentBook.getIconID());

            // Tittle:
            TextView titleText = (TextView) itemView.findViewById(R.id.booktxt_Title);
            titleText.setText(currentBook.getBibText2());

            // Author:
            TextView authorText = (TextView) itemView.findViewById(R.id.booktxt_Author);
            authorText.setText("" + currentBook.getBibText1());

            // Status:
            TextView statusText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(currentBook.getItemStatusCode());

            return itemView;
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
