package edu.ithaca.iclibrary;

/**
 * Created by Yaw P. Aidoo.
 * Stores information about a book from the library( this is just to test the favorite activity)
 */
public class CheckedFavBks {
    private String tittle;
    private String author;
    private int iconID;
    private String status;
    private String isbn;

    public CheckedFavBks(String tittle, String author, int iconID, String status, String isbn) {
        super();
        this.tittle = tittle;
        this.author = author;
        this.iconID = iconID;
        this.status = status;
        this.isbn = isbn;
    }

    public String getTittle() {
        return tittle;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {return isbn;}

    public int getIconID() {

        return iconID;
    }

    public String getStatus(){
        return status;
    }
}

