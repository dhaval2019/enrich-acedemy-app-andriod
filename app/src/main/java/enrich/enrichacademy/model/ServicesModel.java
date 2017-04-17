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

    public TimingModel TimingModel;

    protected ServicesModel(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        timings = in.createStringArray();
        description = in.readString();
        TopologyId = in.readInt();
        ActualPrice = in.readDouble();
        DiscountPrice = in.readDouble();
        TimingModel = in.readParcelable(enrich.enrichacademy.model.TimingModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(name);
        dest.writeStringArray(timings);
        dest.writeString(description);
        dest.writeInt(TopologyId);
        dest.writeDouble(ActualPrice);
        dest.writeDouble(DiscountPrice);
        dest.writeParcelable(TimingModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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
    public List<String> getChildList() {
        return Arrays.asList(description);
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
