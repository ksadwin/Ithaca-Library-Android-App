package edu.ithaca.iclibrary;

import android.content.Intent;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBooks extends AppCompatActivity {
    private List<Material> myBooks = new ArrayList<Material>();
    private MaterialCoder matMaker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matMaker = new MaterialCoder(getApplicationContext());

        populateBookList();

        //Build adapter
        MyListAdapter adapter = new MyListAdapter();

        //configure the list view
        ListView list = (ListView) findViewById(R.id.favListView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoriteBooks.this , DetailActivity.class);
                Material toBePassed = myBooks.get(i);
                String itemData = MaterialCoder.encode(toBePassed).toString();
                intent.putExtra("Detail",itemData);
                startActivity(intent);
            }
        });

    }

    private void populateBookList() {
        List<JSONObject> jsonFavs = matMaker.unpack(matMaker.getFileDirectoryPath());
        List<Material> favMats = matMaker.decode(jsonFavs);
        myBooks = favMats;
    }

    private void makeDetailActivity(Material book) {
        Intent i = new Intent(this, FavoriteDetailActivity.class);
        i.putExtra("bibtext1", book.getBibText1());
        i.putExtra("bibtext2", book.getBibText2());
        i.putExtra("status", book.translateItemStatusCode());
        i.putExtra("isbn", book.getIsbn());
        startActivity(i);
    }

    private class MyListAdapter extends ArrayAdapter<Material> {
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
            final Material currentBook = myBooks.get(position);

            // Fill the view with a book cover
            ImageView imageView = (ImageView) itemView.findViewById(R.id.bookCover);
            imageView.setImageResource(R.drawable.iclogo);

            // Title:
            TextView tittleText = (TextView) itemView.findViewById(R.id.booktxt_Title);
            tittleText.setText(currentBook.getBibText1());

            // Author:
            TextView authorText = (TextView) itemView.findViewById(R.id.booktxt_Author);
            authorText.setText(currentBook.getBibText2());

            // Status:
            TextView statusText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            statusText.setText(currentBook.translateItemStatusCode());

            // ISBN:
            TextView isbnText = (TextView) itemView.findViewById(R.id.booktxt_Status);
            isbnText.setText(currentBook.getIsbn());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeDetailActivity(currentBook);
                }
            });
            return itemView;

        }
    }

}
