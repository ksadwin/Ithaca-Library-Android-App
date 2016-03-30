package edu.ithaca.iclibrary;

/**
 * Created by Kelly on 3/29/2016.
 */
public class Material {
    private String author;
    private String title;
    private String isbn;

    public Material(String author, String title, String isbn) {
        this.author = author;
        this.isbn = isbn;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }
}
