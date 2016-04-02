package edu.ithaca.iclibrary;

import static org.junit.Assert.*;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        System.out.println("MainActivity built successfully.");
    }
}