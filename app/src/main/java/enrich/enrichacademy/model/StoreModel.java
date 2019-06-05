package enrich.enrichacademy.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by varunbarve on 05/10/17.
 */

public class StoreModel implements Parcelable {

    public String address;
    public String name;
    public int id;
    public String landmark;
    public String contactNumber;
    public String latitude;
    public String longitude;
    public int cityId;
    public String cityName;
    public int isActive;

    protected StoreModel (Parcel in) {
        address = in.readString();
        name = in.readString();
        id = in.readInt();
        landmark = in.readString();
        contactNumber = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        cityId = in.readInt();
        cityName = in.readString();
        isActive = in.readInt();
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(landmark);
        dest.writeString(contactNumber);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeInt(cityId);
        dest.writeString(cityName);
        dest.writeInt(isActive);
    }

    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel (Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray (int size) {
            return new StoreModel[size];
        }
    };

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getLandmark () {
        return landmark;
    }

    public void setLandmark (String landmark) {
        this.landmark = landmark;
    }

    public String getContactNumber () {
        return contactNumber;
    }

    public void setContactNumber (String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLatitude () {
        return latitude;
    }

    public void setLatitude (String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude () {
        return longitude;
    }

    public void setLongitude (String longitude) {
        this.longitude = longitude;
    }

    public int getCityId () {
        return cityId;
    }

    public void setCityId (int cityId) {
        this.cityId = cityId;
    }

    public String getCityName () {
        return cityName;
    }

    public void setCityName (String cityName) {
        this.cityName = cityName;
    }

    public int getIsActive () {
        return isActive;
    }

    public void setIsActive (int isActive) {
        this.isActive = isActive;
    }
}
