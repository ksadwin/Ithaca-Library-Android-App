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

    public static String getText(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.next() == XmlPullParser.TEXT) {
            return parser.getText();
        }
        return null;
    }

    public static Material readMaterial(XmlPullParser parser) throws XmlPullParserException, IOException {
        Material m = new Material();
        int eventType = parser.getEventType();
        while (!(eventType == XmlPullParser.END_TAG && parser.getName().equals("sear:result"))) {
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
     * Parses XML in InputStream (from doInBackground).
     * @param in
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
                if (parser.getName().equals("sear:result")) {
                    books.add(readMaterial(parser));
                }
            }
            eventType = parser.next();
        }
        return books;
    }

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
     * Make API request to Ithaca Library and parse XML response.
     * @param params: eventually that will contain the URLs to load but presently it does not
     * @return array of Material objects created from XML found at URL
     */
    protected Material[] doInBackground(URL... params) {
        List<Material> books = new ArrayList<>();
        for (URL u : params) {
            books.addAll(getMaterialsFromLibrary(u));
        }
        return books.toArray(new Material[books.size()]);
    }

    protected void onPostExecute (Material[] materials) {
        for (Material m : materials) {
            Log.v(TAG, m.toString());
        }
    }

}
