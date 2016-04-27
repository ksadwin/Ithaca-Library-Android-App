package edu.ithaca.iclibrary;

/**
 * Created by Joseph on 4/26/2016.
 */


import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;

import org.json.simple.*;

public class MaterialCoderTest {

    MaterialCoder matMaker = new MaterialCoder();

    Material mockMat = new Material("12345", "Chuckie Dickens", "Story About Two Towns",
            "1800", "67890", "The Bookshelf on the Right", 1, 1, 1, "1234567890");

    JSONObject mockMatJSON = new JSONObject();

    @Test
    public void matToJSONReturnsJSONObject() throws Exception{
        JSONObject obj = matMaker.encode(mockMat);

        assertNotNull(obj);
    }
}
