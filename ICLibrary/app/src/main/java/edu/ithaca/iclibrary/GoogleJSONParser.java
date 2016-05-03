package edu.ithaca.iclibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * Converts InputStream text to string.
     * Found at http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
     * @param is: InputStream (for instance as received from HttpUrlConnection
     * @return
     */
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Creates request URL for Google Books API. No OAuth required for this operation.
     * @param query: search term
     * @return properly formatted URL containing query terms
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    public static URL makeGoogleURL(String query) throws MalformedURLException, URISyntaxException {
        URI uri = new URI("https", "//www.googleapis.com/books/v1/volumes?q="+query+"&maxResults=1&projection=lite", null);
        return uri.toURL();
    }

    /**
     * Makes HTTP request and receives InputStream response in JSON format.
     * @param url: URL formatted to make Google Books API request
     * @return InputStream of JSON response
     */
    public static InputStream getJSONFromGoogle(URL url) {
        HttpsURLConnection conn = null;
        try {
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                return in;
            }
            else {
                Log.w(TAG, "HTTPS Response code: "+conn.getResponseCode());
            }
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * Parses JSON from Google Books to retrieve cover image url attribute.
     * @param json: JSONObject from HTTP API request to Google Books
     * @return String url
     */
    public static String getImageURLfromJSON(JSONObject json){
        try {
            JSONObject imageLinks = json.getJSONObject("imageLinks");
            return imageLinks.getString("thumbnail");
        } catch (JSONException e) {
            Log.w(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Parses JSON from Google Books to retrieve description attribute.
     * @param json: JSONObject from HTTP API request to Google Books
     * @return String description
     */
    public static String getDescriptionFromJSON(JSONObject json) {
        try {
            return json.getString("description");
        } catch (JSONException e) {
            Log.w(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Converts initial inputstream from Google Books API request to JSON,
     * then extracts and returns first result.
     * JSON architecture (of relevant items) is as follows:
     * "items": JSONArray
     *    index 0: JSONObject
     *      "description": String
     *      "imageLinks": JSONObject
     *          "thumbnail": String
     * @param in: InputStream from API request
     * @return JSONObject of first item in search results
     * @throws JSONException if results are not formatted as expected (i.e. books not found)
     */
    public static JSONObject getFirstBookFromJSONStream(InputStream in) throws JSONException {
        String jsonString = convertStreamToString(in);
        Log.d(TAG, "HELLO?????");
        JSONObject fullJson = new JSONObject(jsonString);
        JSONArray volumeList = fullJson.getJSONArray("items");
        return volumeList.getJSONObject(0);
    }

    /**
     * Fetches image at URL and returns decoded stream as bitmap
     * @param url: location of image
     * @return Bitmap, or null if no image found
     */
    public static Bitmap getImageFromURL(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                // this line creates & returns bitmap
                return BitmapFactory.decodeStream(in);
            }
            else {
                Log.w(TAG, "HTTP Response code: "+conn.getResponseCode());
            }
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * AsyncTask to make Google Books API requests to obtain image and description of material.
     */
    public class GoogleBooksRequest extends AsyncTask<String, Void, String> {
        public static final String TAG = "GoogleBooksRequest";

        /**
         * Makes Google Books API requests using params as queries to retrieve image & description
         * @param params: query term for book (possibly ISBN?)
         * @return String url for material cover image
         */
        @Override
        protected String doInBackground(String... params) {
            for (String q : params) {
                try {
                    URL url = makeGoogleURL(q);
                    InputStream in = getJSONFromGoogle(url);
                    // if a first book is not found, will throw JSONException and continue
                    JSONObject json = getFirstBookFromJSONStream(in);
                    String desc = getDescriptionFromJSON(json);
                    // TODO: update description in Activity IF not null
                    String imageUrl = getImageURLfromJSON(json);
                    in.close();
                    return imageUrl;
                } catch (URISyntaxException | IOException | NullPointerException | JSONException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
            return null;
        }

        /**
         * Makes an HTTP request for the image URL returned by doInBackground and sets ImageView
         * @param imageUrl: String url to look up
         */
        @Override
        protected void onPostExecute(String imageUrl) {
            // imageview should be already set to default image
            if (imageUrl != null) {
                try {
                    URL url = new URL(imageUrl);
                    Bitmap img = getImageFromURL(url);
                    if (img != null) {
                        // TODO: set ImageView to found image
                    }
                } catch (MalformedURLException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        }
    }

}
