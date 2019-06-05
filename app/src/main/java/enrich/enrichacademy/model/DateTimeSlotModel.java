package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 21/07/17.
 */

public class DateTimeSlotModel implements Parcelable {

    @SerializedName("slotDate")
    public String SlotDate;

    @SerializedName("timeSlotId")
    public int DateTimeSlotId;

    @SerializedName("timeSlots")
    public TimeSlotModel[] TimeSlots;

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {

    }
}
