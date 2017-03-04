package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 22-Feb-17.
 */

public class ServicesModel implements Parent<String>, Parcelable {

    @SerializedName("Id")
    public int Id;

    @SerializedName("Name")
    public String name;

    public String[] timings;

    @SerializedName("Description")
    public String description;

    @SerializedName("Topology_Id")
    public int TopologyId;

    @SerializedName("ActualPrice")
    public double ActualPrice;

    @SerializedName("DiscountPrice")
    public double DiscountPrice;

    public int selectedTimeSlotId;


    protected ServicesModel(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        timings = in.createStringArray();
        description = in.readString();
        TopologyId = in.readInt();
        ActualPrice = in.readDouble();
        DiscountPrice = in.readDouble();
        selectedTimeSlotId = in.readInt();
    }

    public static final Creator<ServicesModel> CREATOR = new Creator<ServicesModel>() {
        @Override
        public ServicesModel createFromParcel(Parcel in) {
            return new ServicesModel(in);
        }

        @Override
        public ServicesModel[] newArray(int size) {
            return new ServicesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(name);
        parcel.writeStringArray(timings);
        parcel.writeString(description);
        parcel.writeInt(TopologyId);
        parcel.writeDouble(ActualPrice);
        parcel.writeDouble(DiscountPrice);
        parcel.writeInt(selectedTimeSlotId);
    }

    @Override
    public List<String> getChildList() {
        return Arrays.asList(description);
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
