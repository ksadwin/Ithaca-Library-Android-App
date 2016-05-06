package edu.ithaca.iclibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FavoriteDetailActivity extends DetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("FavoriteDetailActivity","THIS IS A FAVORITE DETAIL ACTIVITY");

        Button removeMat = (Button) findViewById(R.id.remButton);
        removeMat.setVisibility(View.VISIBLE);
        removeMat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                matMaker.remove(ScrollingActivity.getCurrBook());
                Toast.makeText(FavoriteDetailActivity.this,"This item has been removed from Favorites",Toast.LENGTH_LONG);
                //Intent intent = new Intent(DetailActivity.this, ScrollingActivity.class);
                //intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                //startActivity(intent);
            }
        });
    }

}
