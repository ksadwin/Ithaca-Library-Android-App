package edu.ithaca.iclibrary;

/**
 * Class to represent books/items in the library.
 * Member variables are derived from library XML response.
 * Created by Kelly on 3/29/2016.
 */
public class Material {
    private String bibId; //some kind of internal id number
    private String bibText1; //if author search, author; else title/author/other info
    private String bibText2; //the reverse of bibText1
    private String bibText3; //year
    private String callNumber;
    private String locationName;
    private int mfhdCount; //i have no idea what this is
    private int itemCount;
    private int itemStatusCode;
    private String isbn;

    /**
     *
     * @param bibId: String, internal id number from library
     * @param bibText1: String, primary book descriptor
     * @param bibText2: String, secondary book descriptor
     * @param bibText3: String, tertiary book descriptor
     * @param callNumber: String, call number for location in library
     * @param locationName: String, name of general location in library
     * @param mfhdCount: int, MFHD record
     * @param itemCount: int, number of item available
     * @param itemStatusCode: int, 1 = available, 0 = checked out, -1 = n/a
     * @param isbn: String isbn
     */
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

    public Material() {
        this.bibId = "";
        this.bibText1 = "";
        this.bibText2 = "";
        this.bibText3 = "";
        this.callNumber = "";
        this.locationName = "";
        this.mfhdCount = -1;
        this.itemCount = -1;
        this.itemStatusCode = -1;
        this.isbn = "";
    }

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

    public String translateItemStatusCode() {
        if (itemStatusCode == 1) {
            return "Available";
        } else if (itemStatusCode == 0) {
            return "Checked out";
        } else {
            return "N/A";
        }
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
