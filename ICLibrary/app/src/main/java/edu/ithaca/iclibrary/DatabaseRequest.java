package edu.ithaca.iclibrary;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that makes API requests of the IC Library asynchronously.
 * @author KSADWIN
 * 3/27/2016
 */
public class DatabaseRequest extends AsyncTask<URL, Void, Material[]> {
    private static final String TAG = "DatabaseRequest";

    /**
     * Converts InputStream to String.
     * Found at http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
     * @param is: InputStream to convert to String
     * @return String format of InputStream contents
     */
    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Gets the text inside of an XML tag returned by the library.
     * @param parser XmlPullParser currently in use
     * @return either String text inside tag or null if no text found
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static String getText(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.next() == XmlPullParser.TEXT) {
            return parser.getText();
        }
        return null;
    }

    /**
     * Constructs one Material object from a "sear:result" block of XML.
     * @param parser XmlPullParser currently in use
     * @return Material object with parameters defined by current XML block
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static Material readMaterial(XmlPullParser parser) throws XmlPullParserException, IOException {
        Material m = new Material();
        int eventType = parser.getEventType();
        //parse XML only until the end result tag is found, i.e. that material's definition is over
        while (!(eventType == XmlPullParser.END_TAG && parser.getName().equals("sear:result"))) {
            //find start tags for relevant parameters of material object
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("sear:bibId")) {
                    m.setBibId(getText(parser));
                } else if (parser.getName().equals("sear:bibText1")) {
                    m.setBibText1(getText(parser));
                } else if (parser.getName().equals("sear:bibText2")) {
                    m.setBibText2(getText(parser));
                } else if (parser.getName().equals("sear:bibText3")) {
                    m.setBibText3(getText(parser));
                } else if (parser.getName().equals("sear:callNumber")) {
                    m.setCallNumber(getText(parser));
                } else if (parser.getName().equals("sear:locationName")) {
                    m.setLocationName(getText(parser));
                } else if (parser.getName().equals("sear:isbn")) {
                    m.setIsbn(getText(parser));
                } else if (parser.getName().equals("sear:itemCount")) {
                    try {
                        m.setItemCount(Integer.parseInt(getText(parser)));
                    } catch (NumberFormatException e) {
                        m.setItemCount(-1);
                    }
                } else if (parser.getName().equals("sear:mfhdCount")) {
                    try {
                        m.setMfhdCount(Integer.parseInt(getText(parser)));
                    } catch (NumberFormatException e) {
                        m.setMfhdCount(-1);
                    }
                } else if (parser.getName().equals("sear:itemStatusCode")) {
                    try {
                        m.setItemStatusCode(Integer.parseInt(getText(parser)));
                    } catch (NumberFormatException e) {
                        m.setItemStatusCode(-1);
                    }
                }

            }
            eventType = parser.next();
        }
        return m;
    }

    /**
     * Parses XML from library to generate a list of Material objects.
     * @param in InputStream (from an HTTP request in getMaterialsFromLibrary())
     * @return List<Material>
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static List<Material> parseXML(InputStream in) throws XmlPullParserException, IOException {
        List<Material> books = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(in, null);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                //"sear:result" tag indicates a new result beginning. parse the XML within
                if (parser.getName().equals("sear:result")) {
                    books.add(readMaterial(parser));
                }
            }
            eventType = parser.next();
        }
        return books;
    }

    /**
     * Makes HTTP request to library using the passed URL and returns resultant list of Materials.
     * @param url URL object formatted for appropriate library API call
     * @return List<Material> (may be empty, if no results found/bad HTTP request)
     */
    public static List<Material> getMaterialsFromLibrary(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                return parseXML(in);
            }
            else {
                Log.e(TAG, "HTTP Response code: "+conn.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new ArrayList<Material>();
    }

    /**
     * Called via .execute() in main UI thread to start the whole chain.
     * Sends URLs to getMaterialsFromLibrary() and returns resultant Materials as an ARRAY
     * @param params one or more URL objects (library API calls)
     * @return array of Material objects created from XML found at URL(s)
     */
    protected Material[] doInBackground(URL... params) {
        List<Material> books = new ArrayList<>();
        for (URL u : params) {
            books.addAll(getMaterialsFromLibrary(u));
        }
        return books.toArray(new Material[books.size()]);
    }

    /**
     * Automatically occurs after the .execute() call in the main UI thread.
     * Currently, prints Material objects to log. Ultimate goal: send to ResultViewActivity.
     * @param materials automatically passed from results of doInBackground()
     */
    protected void onPostExecute (Material[] materials) {
        for (Material m : materials) {
            Log.v(TAG, m.toString());
        }
    }

}
