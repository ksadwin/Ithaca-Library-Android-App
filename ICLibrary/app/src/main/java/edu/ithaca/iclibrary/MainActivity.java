package edu.ithaca.iclibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Activity responsible for collecting user search
 * queries. Will also be the gateway to the barcode scanner
 * functionality.
 */
public class MainActivity extends AppCompatActivity {
    //for logging
    private static final String TAG = "MainActivity";

    /**
     * Given query type and query text, constructs URL for accessing IC Library database
     * @param code String, query type. Should be one of the following codes (but probably not GKEY)
     *             isbn:isbn, TALL:title, GKEY:key, SKEY:subject, NKEY:author
     * @param query String, query text.
     * @return URL object
     * @throws MalformedURLException
     */
    public static URL makeURL(String code, String query) throws MalformedURLException{
        return new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode="+code+
                "&maxResultsPerPage=25&recCount=25&searchArg="+query);
    }

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


        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        //Creates searchbar dynamically. must be final to access text in event listener?
        final EditText searchBar = new EditText(this);

        //Hint text will disappear when the user begins typing. You're welcome. -ksadwin
        searchBar.setHint("Enter your search here.");

        RelativeLayout.LayoutParams rightOf = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightOf.addRule(RelativeLayout.RIGHT_OF, R.id.search_button);
        searchBar.setLayoutParams(rightOf);
        mainLayout.addView(searchBar);

        //adding functionality to search button -ksadwin
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.v(TAG, "query: "+searchBar.getText().toString());
                DatabaseRequest req = new DatabaseRequest();
                try {
                    req.execute(makeURL("NKEY", searchBar.getText().toString()));
                } catch (MalformedURLException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }
}
