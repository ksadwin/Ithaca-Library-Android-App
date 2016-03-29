package edu.ithaca.iclibrary;

import org.junit.Test;

import java.net.URL;

/**
 * Created by Kelly on 3/29/2016.
 */
public class DatabaseRequestTest {

    @Test
    public void testingTests() {
        DatabaseRequest req = new DatabaseRequest();
        req.execute(new URL[0]);
        System.out.println("does this run");
    }
}
