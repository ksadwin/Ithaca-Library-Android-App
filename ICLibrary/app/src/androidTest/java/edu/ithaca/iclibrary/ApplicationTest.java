package edu.ithaca.iclibrary;

import android.app.Application;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        onData(allOf(is(instanceOf(String.class)),is("Subject"))).
                perform(click());

        onView(ViewMatchers.withId(R.id.searchType)).
                perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Title"))).
                perform(click());
    }
}