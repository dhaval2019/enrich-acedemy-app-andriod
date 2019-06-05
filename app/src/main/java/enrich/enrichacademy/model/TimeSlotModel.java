package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 21/07/17.
 */

public class TimeSlotModel implements Parcelable, Comparable<TimeSlotModel>{

    @SerializedName("id")
    public int Id;

    @SerializedName("start")
    public String Start;

    @SerializedName("end")
    public String End;

    @SerializedName("order")
    public int Order;

    public boolean isSelected;

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {

    }

    @Override
    public int compareTo (@NonNull TimeSlotModel o) {
        return o.Order;
    }
}
