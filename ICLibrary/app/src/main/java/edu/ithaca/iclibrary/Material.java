package edu.ithaca.iclibrary;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kelly on 3/29/2016.
 */
public class Material implements Parcelable {
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

    protected Material(Parcel in) {
        bibId = in.readString();
        bibText1 = in.readString();
        bibText2 = in.readString();
        bibText3 = in.readString();
        callNumber = in.readString();
        locationName = in.readString();
        mfhdCount = in.readInt();
        itemCount = in.readInt();
        itemStatusCode = in.readInt();
        isbn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bibId);
        dest.writeString(bibText1);
        dest.writeString(bibText2);
        dest.writeString(bibText3);
        dest.writeString(callNumber);
        dest.writeString(locationName);
        dest.writeInt(mfhdCount);
        dest.writeInt(itemCount);
        dest.writeInt(itemStatusCode);
        dest.writeString(isbn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

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
