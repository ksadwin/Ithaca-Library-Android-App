package edu.ithaca.iclibrary;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that makes API requests of the IC Library asynchronously.
 * @author KSADWIN
 * @exception IOException, XmlPullParserException
 * 3/27/2016
 */
public class DatabaseRequest extends AsyncTask<URL, String, String> {

    /**
     * Found at http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
     * @param is: InputStream to convert to String
     * @return String format of InputStream contents
     */
    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Make API request to Ithaca Library and parse XML response.
     * @param params: eventually that will contain the URLs to load but presently it does not
     * @return parsed XML response. In what format? Why do you ask such hard questions?
     */
    protected String doInBackground(URL[] params) {
        String responseString = null;
        try {
            URL url = new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode=NKEY&maxResultsPerPage=25&recCount=25&searchArg=dickens");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(conn.getInputStream());
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(in, null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        System.out.println("Start document");
                    } else if(eventType == XmlPullParser.END_DOCUMENT) {
                        System.out.println("End document");
                    } else if(eventType == XmlPullParser.START_TAG) {
                        System.out.println("Start tag "+parser.getName());
                    } else if(eventType == XmlPullParser.END_TAG) {
                        System.out.println("End tag "+parser.getName());
                    } else if(eventType == XmlPullParser.TEXT) {
                        System.out.println("Text "+parser.getText());
                    }
                    eventType = parser.next();
                }
            }
            else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //System.out.print(responseString);
        return null;
    }
}
