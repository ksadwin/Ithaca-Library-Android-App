package edu.ithaca.iclibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBooks extends AppCompatActivity {
    private List<CheckedFavBks> myBooks = new ArrayList<CheckedFavBks>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateBookList();
        populateListView();
        registerItemClick();

    }
    //this will be redefined to use Joe's methods
    private void populateBookList() {
        myBooks.add(new CheckedFavBks("Tank and AVF", "Kelly Sadwin",  R.drawable.iclogo, "Available","0112358475"));
        myBooks.add(new CheckedFavBks("Tess", "Prof. Noboby",  R.drawable.iclogo, "Available","0112358475"));
        myBooks.add(new CheckedFavBks("Pride", "Robin Stevens",  R.drawable.iclogo, "Checked Out","0112358475"));
        myBooks.add(new CheckedFavBks("Naked Pictures", "Jon Stewart",  R.drawable.iclogo, "Checked Out!","0112358475"));
        myBooks.add(new CheckedFavBks("Letters for Scarlet", "Julie Gardner",  R.drawable.iclogo, "Checked Out","0112358475"));
        myBooks.add(new CheckedFavBks("Adaptive Web Design", "Aaron Gusatfson", R.drawable.iclogo, "Available", "0112358475"));
        myBooks.add(new CheckedFavBks("Book Covers", "Trevor Wheeler",  R.drawable.iclogo, "Available", "0112358475"));
        myBooks.add(new CheckedFavBks("Passport", "Immigration Office", R.drawable.iclogo, "Checked Out", "0112358475"));
    }

    private void populateListView() {
        //Build adapter
        ArrayAdapter<CheckedFavBks> adapter = new MyListAdapter();

        //configure the list view
        ListView list = (ListView) findViewById(R.id.favListView);
        list.setAdapter(adapter);
    }

    private void registerItemClick() {
        ListView list = (ListView) findViewById(R.id.favListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                CheckedFavBks clickedBook = myBooks.get(position);
                String message = "You clicked position " + position
                        + " Which is Book Tittle " + clickedBook.getTittle();
                Toast.makeText(FavoriteBooks.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<CheckedFavBks> {
        public MyListAdapter() {
            super(FavoriteBooks.this, R.layout.content_favorite, myBooks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // This will make sure there is a view to work with (may be null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.content_favorite, parent, false);
            }

            // Find the Book to work with.
            CheckedFavBks currentBook = myBooks.get(position);

            // Fill the view with a book cover
            ImageView imageView = (ImageView) itemView.findViewById(R.id.bookCover);
            imageView.setImageResource(currentBook.getIconID());

            // Tittle:
            TextView tittleText = (TextView) itemView.findViewById(R.id.booktxt_Title);
            tittleText.setText(currentBook.getTittle());

            // Author:
            TextView authorText = (TextView) itemView.findViewById(R.id.booktxt_Author);
            authorText.setText("" + currentBook.getAuthor());

            // Status:
            TextView statusText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(currentBook.getStatus());

            // ISBN:
            TextView isbn = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(currentBook.getStatus());

            return itemView;
        }
    }

}
