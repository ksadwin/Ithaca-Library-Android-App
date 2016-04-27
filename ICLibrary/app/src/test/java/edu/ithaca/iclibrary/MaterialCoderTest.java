package edu.ithaca.iclibrary;

/**
 * Created by Joseph on 4/26/2016.
 */


import org.junit.Test;

import org.json.simple.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MaterialCoderTest {

    MaterialCoder matMaker = new MaterialCoder();

    Material mockMat = new Material("12345", "Chuckie Dickens", "Story About Two Towns",
            "1800", "67890", "The Bookshelf on the Right", 1, 1, 1, "1234567890");

    JSONObject test = new JSONObject();

    /**
     * Fills test with expected values manually.
     */
    public void setup(){
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
    }

    @Test
    public void matToJSONReturnsJSONObject() throws Exception{
        JSONObject obj = matMaker.encode(mockMat);

        assertEquals(obj.toString(), test.toString(),false);

    }
}
