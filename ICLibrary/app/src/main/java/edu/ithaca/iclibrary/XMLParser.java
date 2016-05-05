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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that makes API requests of the IC Library asynchronously.
 * @author KSADWIN
 * 3/27/2016
 */
public class XMLParser {
    private static final String TAG = "XMLParser";

    /**
     * Given query type and query text, constructs URL for accessing IC Library database
     * @param code String, query type. Should be one of the following codes (but probably not GKEY)
     *             isbn:isbn, TALL:title, GKEY:key, SKEY:subject, NKEY:author
     * @param query String, query text.
     * @return URL object
     * @throws MalformedURLException, URISyntaxException
     */
    public static URL makeURL(String code, String query) throws MalformedURLException, URISyntaxException {
        URI uri = new URI("http", "//phoebe.ithaca.edu:7014/vxws/SearchService?searchCode="+code+
                "&maxResultsPerPage=25&recCount=25&searchArg="+query, null);
        return uri.toURL();
    }


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
     * Pulls text from the XML parser's current tag.
     * @param parser: the in-use XmlPullParser in which the current tag's text is desired
     * @return either a String or null
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
     * Constructs a Material object from a nested group of XML tags as determined by the format of
     * the IC Library's HTTP response.
     * @param parser: in-use XmlPullParser at the tag that groups inner tags pertaining to one item
     * @return Material object
     * @throws XmlPullParserException
     * @throws IOException
     */
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
        Log.v(TAG, m.toString());
        return m;
    }

    /**
     * Parses XML in InputStream (from doInBackground).
     * @param in: InputStream from HttpUrlConnection response
     * @return List<Material> of items found
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

    /**
     * Makes HTTP request at url and sends response to parser functions,
     * @param url: URL constructed using IC Library API.
     * @return List<Materials> of found Materials, or an empty list if anything goes wrong.
     */
    public static List<Material> getMaterialsFromLibrary(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                List<Material> materials = parseXML(in);
                in.close();
                return materials;
            }
            else {
                Log.w(TAG, "HTTP Response code: " + conn.getResponseCode());
            }
        } catch (IOException|XmlPullParserException e) {
            Log.w(TAG, e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new ArrayList<Material>();
    }


}
