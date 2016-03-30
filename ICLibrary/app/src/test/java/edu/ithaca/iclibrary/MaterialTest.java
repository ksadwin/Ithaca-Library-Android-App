package edu.ithaca.iclibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kelly on 3/29/2016.
 */
public class MaterialTest {
    @Test
    public void verifyMaterialConstructor() {
        String title = "title";
        String author = "author";
        String isbn = "isbn";
        Material m = new Material(author, title, isbn);
        assertEquals(m.getTitle(), title);
        assertEquals(m.getAuthor(), author);
        assertEquals(m.getIsbn(), isbn);
    }
}
