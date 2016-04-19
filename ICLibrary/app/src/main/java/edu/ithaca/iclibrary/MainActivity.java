package edu.ithaca.iclibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The Activity responsible for collecting user search
 * queries. Will also be the gateway to the barcode scanner
 * functionality.
 */
public class MainActivity extends AppCompatActivity {
    //for logging
    private static final String TAG = "MainActivity";



    /**
     * Sets view with activity_main XML file.
     * Creates an EditText searchbar dynamically with
     * Java.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get search bar from XML & make it final so that event listener can access text
        final EditText searchBar = (EditText) findViewById(R.id.editQuery);

        final Spinner searchType = spinnerLoader(R.id.searchType);

        //adding functionality to search button & spinner
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.v(TAG, "query: "+searchBar.getText().toString()+"\ntype: "+searchType.getSelectedItem().toString());
                String queryType = "";
                if (searchType.getSelectedItem().toString().equals("Title")) {
                    queryType = "TALL";
                } else if (searchType.getSelectedItem().toString().equals("Author")) {
                    queryType = "NKEY";
                } else if (searchType.getSelectedItem().toString().equals("ISBN")) {
                    queryType = "isbn";
                } else if (searchType.getSelectedItem().toString().equals("Subject")) {
                    queryType = "SKEY";
                } else {
                    //This query type is called "key" in Mariah's code. Not sure what it means
                    queryType = "GKEY";
                }

                String[] queryArray = {searchBar.getText().toString(), queryType};

                //only proceed if user entered text
                if (!queryArray[0].equals("")) {
                    //Create and display the search result activity.
                    // Displays nothing for the moment.
                    makeResultsActivity(queryArray);
                }

            }
        });
    }

    public Spinner spinnerLoader(int id) {
        Spinner searchType = (Spinner)findViewById(id);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        searchType.setAdapter(adapter);

        return searchType;
    }

    /**
     * Creates and transitions to the Results View Activity from MainActivity.
     * @param query: String array of length 2, where [0] is query and [1] is type, to be passed to next Activity.
     */
    public void makeResultsActivity(String[] query) {
        //Create and display the search result activity.
        // Displays nothing for the moment.
        Intent results = new Intent(this, ScrollingActivity.class);
        results.putExtra("query_terms", query);
        startActivity(results);
    }
}
