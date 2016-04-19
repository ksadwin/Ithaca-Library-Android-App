package edu.ithaca.iclibrary;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

/**
 * Created by Kelly on 4/13/2016.
 */
public class DatabaseRequestTest {

    @Test
    public void dbRequestMakesMaterialList() throws Exception {
        List<Material> l = DatabaseRequest.getMaterialsFromLibrary(
                new URL("http://phoebe.ithaca.edu:7014/vxws/SearchService?searchCode=NKEY&maxResultsPerPage=25&recCount=25&searchArg=dickens"));
        assertNotNull(l);
    }
}
