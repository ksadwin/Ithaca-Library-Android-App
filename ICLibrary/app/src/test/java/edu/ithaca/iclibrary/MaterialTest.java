package edu.ithaca.iclibrary;

import android.os.Parcel;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Kelly on 4/19/2016.
 */
public class MaterialTest {

    @Test
    public static void testMaterialParcelability() {
        Material normalInput = new Material("12345", "Chuckie Dickens", "Story About Two Towns",
                "1800", "67890", "The Bookshelf on the Right", 1, 1, 1, "1234567890");
        Parcel normalParcel = Parcel.obtain();
        //normalInput.writeToParcel(normalParcel, null);
    }

}
