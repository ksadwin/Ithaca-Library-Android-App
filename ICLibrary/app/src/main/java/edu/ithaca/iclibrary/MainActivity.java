package edu.ithaca.iclibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The Activity responsible for collecting user search
 * queries. Will also be the gateway to the barcode scanner
 * functionality.
 */
public class MainActivity extends AppCompatActivity {

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

        //Creates searchbar dynamically.
        EditText searchBar = new EditText(this);
        searchBar.setText("Enter your search here.");
        RelativeLayout.LayoutParams rightOf = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightOf.addRule(RelativeLayout.RIGHT_OF, R.id.search_button);
        searchBar.setLayoutParams(rightOf);
        mainLayout.addView(searchBar);
    }
}
