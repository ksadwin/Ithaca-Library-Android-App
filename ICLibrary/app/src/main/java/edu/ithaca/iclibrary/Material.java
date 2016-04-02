package edu.ithaca.iclibrary;

/**
 * Created by Kelly on 3/29/2016.
 */
public class Material {
    private String bibId; //some kind of internal id number
    private String bibText1; //author
    private String bibText2; //description with title, author, and more
    private String bibText3; //year
    private String callNumber;
    private String locationName;
    private int mfhdCount; //i have no idea what this is
    private int itemCount;
    private int itemStatusCode;
    private String isbn;

    public Material(String bibId, String bibText1, String bibText2, String bibText3,
                    String callNumber, String locationName, int mfhdCount, int itemCount,
                    int itemStatusCode, String isbn) {
        this.bibId = bibId;
        this.bibText1 = bibText1;
        this.bibText2 = bibText2;
        this.bibText3 = bibText3;
        this.callNumber = callNumber;
        this.locationName = locationName;
        this.mfhdCount = mfhdCount;
        this.itemCount = itemCount;
        this.itemStatusCode = itemStatusCode;
        this.isbn = isbn;
    }

    public Material() {}

    public String getBibId() {
        return bibId;
    }

    public void setBibId(String bibId) {
        this.bibId = bibId;
    }

    public String getBibText1() {
        return bibText1;
    }

    public void setBibText1(String bibText1) {
        this.bibText1 = bibText1;
    }

    public String getBibText2() {
        return bibText2;
    }

    public void setBibText2(String bibText2) {
        this.bibText2 = bibText2;
    }

    public String getBibText3() {
        return bibText3;
    }

    public void setBibText3(String bibText3) {
        this.bibText3 = bibText3;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getMfhdCount() {
        return mfhdCount;
    }

    public void setMfhdCount(int mfhdCount) {
        this.mfhdCount = mfhdCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemStatusCode() {
        return itemStatusCode;
    }

    public void setItemStatusCode(int itemStatusCode) {
        this.itemStatusCode = itemStatusCode;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return "ID "+bibId+"\n"+bibText1+"\n"+bibText2+"\n"+bibText3+"\n"+callNumber+"\n"+locationName
                +"\n"+isbn+"\n"+mfhdCount+"\t"+itemCount+"\t"+itemStatusCode;
    }
}
