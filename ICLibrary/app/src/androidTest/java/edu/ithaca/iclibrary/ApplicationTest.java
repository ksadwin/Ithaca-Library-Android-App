package edu.ithaca.iclibrary;

import android.app.Application;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.graphics.Bitmap;
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
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    String oneWordQuery = "dickens";
    String multiWordQuery = "The Cat in the Hat";

    @Rule
    public ActivityTestRule<MainActivity> mainTest =
            new ActivityTestRule<>(MainActivity.class);

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
}