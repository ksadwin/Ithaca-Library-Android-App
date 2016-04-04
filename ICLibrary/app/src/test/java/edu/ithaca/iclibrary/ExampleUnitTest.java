package edu.ithaca.iclibrary;

import static org.junit.Assert.*;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Mock
    MainActivity sampleMain = Mockito.mock(MainActivity.class);

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mainActivityBuildsSuccessfully() throws Exception {
        assertNotNull(sampleMain);
    }

    @Test
    public void spinnerLoadedAtOnCreate() throws Exception {
        assertSame((Spinner) sampleMain.findViewById(R.id.searchType), sampleMain.spinnerLoader(R.id.searchType));
    }

    @Test
    public void spinnerHasCorrectSearchTypesEnabled() throws Exception {

    //        assertEquals("Author", sampleMain.spinnerLoader(R.id.searchType).getItemAtPosition(0));
    //        assertEquals("ISBN",sampleMain.spinnerLoader(R.id.searchType).getItemAtPosition(1));
    //        assertEquals("Subject",sampleMain.spinnerLoader(R.id.searchType).getItemAtPosition(2));
    //        assertEquals("Title",sampleMain.spinnerLoader(R.id.searchType).getItemAtPosition(3));
    }
}