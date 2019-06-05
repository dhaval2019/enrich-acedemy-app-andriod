package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by varunbarve on 05/10/17.
 */

public class CityModel implements Parcelable{

    private int id;
    private String createdOn;
    private String cityName;
    private int isActive;

    public CityModel (int id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCreatedOn () {
        return createdOn;
    }

    public void setCreatedOn (String createdOn) {
        this.createdOn = createdOn;
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


    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {

    }
}
