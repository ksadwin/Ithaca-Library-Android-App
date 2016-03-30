package edu.ithaca.iclibrary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;

/**
 * Created by Kelly on 3/29/2016.
 */
//@RunWith(RoboelectricCustomTestRunner.class)
public class DatabaseRequestTest {

    @Mock DatabaseRequest req;

    @Before
    public void setup () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testingTests() {
        req.execute(new URL[0]);
        System.out.println("does this run");
    }
}
