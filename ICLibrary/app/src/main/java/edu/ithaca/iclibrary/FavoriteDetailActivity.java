package edu.ithaca.iclibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FavoriteDetailActivity extends DetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find the Book to work with.
        try {
            JSONObject json = (JSONObject) new JSONTokener(getIntent().getExtras().getString("book")).nextValue();

            final Material currentBook = MaterialCoder.decode(json);

            Button removeMat = (Button) findViewById(R.id.remButton);
            removeMat.setVisibility(View.VISIBLE);
            removeMat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    matMaker.remove(currentBook);
                    Toast.makeText(FavoriteDetailActivity.this, "This item has been removed from Favorites", Toast.LENGTH_LONG);
                    //Intent intent = new Intent(DetailActivity.this, ScrollingActivity.class);
                    //intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    //startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
