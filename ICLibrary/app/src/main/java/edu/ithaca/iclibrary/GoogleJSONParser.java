package edu.ithaca.iclibrary;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kelly on 4/29/2016.
 */
public class GoogleJSONParser {
    public static final String TAG = "GoogleJSONParser";

    /**
     * Creates request URL for Google Books API. No OAuth required for this operation.
     * @param query: search term
     * @return properly formatted URL containing query terms
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    public static URL makeGoogleURL(String query) throws MalformedURLException, URISyntaxException {
        URI uri = new URI("https", "//www.googleapis.com/books/v1/volumes?q="+query, null);
        return uri.toURL();
    }

    /**
     * Makes HTTP request and receives InputStream response in JSON format.
     * TODO: Process that JSON!
     * @param url: URL formatted to make Google Books API request
     * @return
     */
    public static InputStream getJSONFromGoogle(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                return in;
            }
            else {
                Log.w(TAG, "HTTP Response code: "+conn.getResponseCode());
            }
        } catch (IOException e) {
            Log.w(TAG, e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static String getImageURLfromJSON(InputStream in) {
        return null;
    }

    public static String getDescriptionFromJSON(InputStream in) {
        return null;
    }

    /**
     * AsyncTask to make Google Books API requests to obtain image and description of material.
     */
    public class GoogleBooksRequest extends AsyncTask<String, Void, Void> {
        public static final String TAG = "GoogleBooksRequest";

        @Override
        protected Void doInBackground(String... params) {
            for (String q : params) {
                try {
                    URL url = makeGoogleURL(q);
                    InputStream in = getJSONFromGoogle(url);
                    Log.v(TAG, in.toString());
                } catch (MalformedURLException | URISyntaxException e) {
                    Log.w(TAG, e.toString());
                }
            }
            return null;
        }
    }
}
