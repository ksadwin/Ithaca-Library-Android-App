package edu.ithaca.iclibrary;

import android.os.Parcel;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Kelly on 4/19/2016.
 */
public class MaterialTest {

    @Test
    public static void testMaterial() {
        Material normalInput = new Material("12345", "Chuckie Dickens", "Story About Two Towns",
                "1800", "67890", "The Bookshelf on the Right", 1, 1, 1, "1234567890");

        //normalInput.writeToParcel(normalParcel, null);
    }

}
