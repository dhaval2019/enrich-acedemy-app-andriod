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

    @SerializedName("id")
    public int Id;

    @SerializedName("name")
    public String Name;

    @SerializedName("genderType")
    public int GenderType;

    @SerializedName("duration")
    public int Duration;

    @SerializedName("isActive")
    public int IsActive;

    @SerializedName("topologyId")
    public int TopologyId;

    @SerializedName("storeId")
    public int StoreId;

    @SerializedName("exclusiveAt")
    public int ExclusiveAt;

    @SerializedName("appliedTo")
    public int AppliedTo;

    @SerializedName("availableAt")
    public int AvailableAt;

    @SerializedName("selectedImageWeb")
    public String SelectedImageWeb;

    @SerializedName("unselectedImageWeb")
    public String UnselectedImageWeb;

    @SerializedName("selectedImageMobile")
    public String SelectedImageMobile;

    @SerializedName("unselectedImageMobile")
    public String UnselectedImageMobile;

    @SerializedName("createdAt")
    public String CreatedOn;

    @SerializedName("updatedAt")
    public String UpdatedAt;

    public TimingModel TimingModel;

    public TimeSlotModel timeSlotModel;

//    public String name;

    public String[] timings;

    @SerializedName("description")
    public String Description;

    @SerializedName("price")
    public double Price;

    @SerializedName("discountPrice")
    public double DiscountPrice;

    public double ActualPrice;

    public int dateTimeSlotId;

    public String slotDate;

    public String dataType;

    public StoreModel storeModel;

    public ServicesModel() {
    }

    protected ServicesModel(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        GenderType = in.readInt();
        Duration = in.readInt();
        IsActive = in.readInt();
        TopologyId = in.readInt();
        StoreId = in.readInt();
        ExclusiveAt = in.readInt();
        AppliedTo = in.readInt();
        AvailableAt = in.readInt();
        SelectedImageWeb = in.readString();
        UnselectedImageWeb = in.readString();
        SelectedImageMobile = in.readString();
        UnselectedImageMobile = in.readString();
        CreatedOn = in.readString();
        UpdatedAt = in.readString();
        TimingModel = in.readParcelable(enrich.enrichacademy.model.TimingModel.class.getClassLoader());
//        name = in.readString();
        timings = in.createStringArray();
        Description = in.readString();
        Price = in.readDouble();
        dateTimeSlotId = in.readInt();
        timeSlotModel = in.readParcelable(TimeSlotModel.class.getClassLoader());
        slotDate = in.readString();
        dataType = in.readString();
        storeModel = in.readParcelable(StoreModel.class.getClassLoader());
        DiscountPrice = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeInt(GenderType);
        dest.writeInt(Duration);
        dest.writeInt(IsActive);
        dest.writeInt(TopologyId);
        dest.writeInt(StoreId);
        dest.writeInt(ExclusiveAt);
        dest.writeInt(AppliedTo);
        dest.writeInt(AvailableAt);
        dest.writeString(SelectedImageWeb);
        dest.writeString(UnselectedImageWeb);
        dest.writeString(SelectedImageMobile);
        dest.writeString(UnselectedImageMobile);
        dest.writeString(CreatedOn);
        dest.writeString(UpdatedAt);
        dest.writeParcelable(TimingModel, flags);
//        dest.writeString(name);
        dest.writeStringArray(timings);
        dest.writeString(Description);
        dest.writeDouble(Price);
        dest.writeInt(dateTimeSlotId);
        dest.writeParcelable(timeSlotModel, flags);
        dest.writeString(slotDate);
        dest.writeString(dataType);
        dest.writeParcelable(storeModel, flags);
        dest.writeDouble(DiscountPrice);
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

    public List<String> getChildList() {
        return Arrays.asList("" + TopologyId);
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
