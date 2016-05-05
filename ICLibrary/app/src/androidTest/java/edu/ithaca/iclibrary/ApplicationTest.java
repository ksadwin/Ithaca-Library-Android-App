package edu.ithaca.iclibrary;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;

import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import java.net.URL;
import java.util.List;
import android.test.InstrumentationTestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mainTest =
            new ActivityTestRule<>(MainActivity.class);

    String oneWordQuery = "dickens";
    String multiWordQuery = "The Cat in the Hat";

    //MaterialCoder test variables
    public MaterialCoder matMaker = new MaterialCoder(mainTest.getActivity());
    public Material mockMat = new Material("12345", "Chuckie Dickens", "Story About Two Towns",
            "1800", "67890", "The Bookshelf on the Right", 1, 1, 1, "1234567890");
    public JSONObject test = new JSONObject();

    @Before
    public void setup(){
        try{
            test.put("bibID","12345");
            test.put("bibText1","Chuckie Dickens");
            test.put("bibText2", "Story About Two Towns");
            test.put("bibText3", "1800");
            test.put("callNumber", "67890");
            test.put("locationName", "The Bookshelf on the Right");
            test.put("mfhdCount", 1);
            test.put("itemCount", 1);
            test.put("itemStatusCode",1);
            test.put("isbn", "1234567890");
        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Test
    public void oneWordStringIsRecorded() throws Exception{
        onView(ViewMatchers.withId(R.id.editQuery)).
                perform(ViewActions.click(), ViewActions.typeText(oneWordQuery));

        Espresso.closeSoftKeyboard();

        onView(ViewMatchers.withId(R.id.editQuery)).
                check(ViewAssertions.matches(ViewMatchers.withText(oneWordQuery)));
    }

    @Test
    public void multiStringIsRecorded() throws Exception{
        onView(ViewMatchers.withId(R.id.editQuery)).
                perform(ViewActions.click(), ViewActions.typeText(multiWordQuery));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.editQuery)).
                check(matches(withText(multiWordQuery)));
    }

    @Test
    public void spinnerSelectionIsChanged() throws Exception{
        //Clicks for every value in searchType. Fails if one isn't found.
        onView(ViewMatchers.withId(R.id.searchType)).
                perform(click());
        onData(allOf(is(instanceOf(String.class)),is("ISBN"))).
                perform(click());

        onView(ViewMatchers.withId(R.id.searchType)).
                perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Author"))).
                perform(click());

        onView(ViewMatchers.withId(R.id.searchType)).
                perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Subject"))).
                perform(click());

        onView(ViewMatchers.withId(R.id.searchType)).
                perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Title"))).
                perform(click());
    }

    @Test
    public void dbRequestMakesMaterialList() throws Exception {

        URL maxResults = new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode=GKEY&maxResultsPerPage=25&recCount=25&searchArg=lit");
        URL avgResults = new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode=NKEY&maxResultsPerPage=25&recCount=25&searchArg=dickens");
        URL noResults = new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode=isbn&maxResultsPerPage=25&recCount=25&searchArg=ASDFGHJKL");
        List<Material> lMax = XMLParser.getMaterialsFromLibrary(maxResults);
        List<Material> lAvg = XMLParser.getMaterialsFromLibrary(avgResults);
        List<Material> lNone = XMLParser.getMaterialsFromLibrary(noResults);
        assertNotNull(lMax);
        assertEquals(25, lMax.size());
        assertNotNull(lAvg);
        assertNotSame(0, lAvg.size());
        assertNotNull(lNone);
        assertEquals(0, lNone.size());
    }
/*
    @Test
    public void googleBooksTest() throws Exception {
        URL notabook = GoogleJSONParser.makeGoogleURL("notabooknotabook");
        InputStream badIn = GoogleJSONParser.getJSONFromGoogle(notabook);
        Log.d("googleBooksTest", GoogleJSONParser.convertStreamToString(badIn));
        JSONObject badJson = new JSONObject(GoogleJSONParser.convertStreamToString(badIn));
        badIn.close();
        String noUrl = GoogleJSONParser.getImageURLfromJSON(badJson);
        String noDesc = GoogleJSONParser.getDescriptionFromJSON(badJson);
        assertNull(noUrl);
        assertNull(noDesc);

        String query = "978-3-16-148410-0";
        URL realBook = GoogleJSONParser.makeGoogleURL(query);
        InputStream in = GoogleJSONParser.getJSONFromGoogle(realBook);
        JSONObject json = GoogleJSONParser.getFirstBookFromJSONStream(in);
        in.close();
        String description = GoogleJSONParser.getDescriptionFromJSON(json);
        assertNotNull(description);

        String imageUrl = GoogleJSONParser.getImageURLfromJSON(json);
        URL goodImgUrl = new URL(imageUrl);
        URL validUrlNoImg = new URL("https://google.com");
        Bitmap goodImg = GoogleJSONParser.getImageFromURL(goodImgUrl);
        Bitmap noImg = GoogleJSONParser.getImageFromURL(validUrlNoImg);
        assertNotNull(goodImg);
        assertNull(noImg);
    }
*/
    /**
     * Tests that MaterialCoder.encode generates a JSON identical to test.
     * @throws Exception
     */
    @Test
    public void encodeReturnsJSONObject() throws Exception{

        JSONObject obj = matMaker.encode(mockMat);

        assertTrue(obj.toString().compareTo(test.toString()) == 0);
    }

    @Test
    public void saveMatWritesFile() throws Exception{
        JSONObject obj = matMaker.encode(mockMat);
        matMaker.saveMat(mockMat);
        FileInputStream favsText = null;
        String sampleMatString;


        try{
            File favs = new File(matMaker.getFileDirectoryPath());
            favsText = new FileInputStream(favs);
        }
        catch(Exception e){
            throw e;
        }
        InputStreamReader isr = new InputStreamReader(favsText);
        BufferedReader br = new BufferedReader(isr);

        try{
            sampleMatString = br.readLine();
        }catch(Exception e){
            throw e;
        }

            assertTrue(((obj.toString()).equals(sampleMatString)));
    }

    @Test
    public void materialFromJsonFunctionality() throws Exception{
        JSONObject obj = matMaker.encode(mockMat);
        Material mat = matMaker.decode(obj);

        assertTrue(obj.toString().equals(matMaker.encode(mat).toString()));

    }
}