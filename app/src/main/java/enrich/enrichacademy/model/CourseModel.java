package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CourseModel implements Parcelable {

    @SerializedName("name")
    public String Name;
    @SerializedName("description")
    public String Description;
    @SerializedName("duration")
    public int Duration;
    @SerializedName("genderType")
    public int GenderType;

    public int TopologyId;
    @SerializedName("storeId")
    public int StoreId;

    public int ExclusiveAt;
    @SerializedName("selectedImageWeb")
    public String SelectedImageWeb;
    @SerializedName("unselectedImageWeb")
    public String UnselectedImageWeb;
    @SerializedName("selectedImageMobile")
    public String SelectedImageMobile;
    @SerializedName("unselectedImageMobile")
    public String UnselectedImageMobile;
    @SerializedName("createdOn")
    public String CreatedOn;
    @SerializedName("updatedAt")
    public String UpdatedAt;
    @SerializedName("courseCategoryId")
    public int CourseCategoryId;
    @SerializedName("isActive")
    public int IsActive;
    @SerializedName("id")
    public int Id;
    @SerializedName("isApplied")
    public int IsApplied;
    @SerializedName("courseStatus")
    public int CourseStatus;

    public String price;

    public String dataType;

    protected CourseModel (Parcel in) {
        Name = in.readString();
        Description = in.readString();
        Duration = in.readInt();
        GenderType = in.readInt();
        TopologyId = in.readInt();
        StoreId = in.readInt();
        ExclusiveAt = in.readInt();
        SelectedImageWeb = in.readString();
        UnselectedImageWeb = in.readString();
        SelectedImageMobile = in.readString();
        UnselectedImageMobile = in.readString();
        CreatedOn = in.readString();
        UpdatedAt = in.readString();
        IsActive = in.readInt();
        Id = in.readInt();
        CourseCategoryId = in.readInt();
        IsApplied = in.readInt();
        CourseStatus = in.readInt();
        dataType = in.readString();
        price = in.readString();
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeInt(Duration);
        dest.writeInt(GenderType);
        dest.writeInt(TopologyId);
        dest.writeInt(StoreId);
        dest.writeInt(ExclusiveAt);
        dest.writeString(SelectedImageWeb);
        dest.writeString(UnselectedImageWeb);
        dest.writeString(SelectedImageMobile);
        dest.writeString(UnselectedImageMobile);
        dest.writeString(CreatedOn);
        dest.writeString(UpdatedAt);
        dest.writeInt(IsActive);
        dest.writeInt(Id);
        dest.writeInt(CourseCategoryId);
        dest.writeInt(IsApplied);
        dest.writeInt(CourseStatus);
        dest.writeString(dataType);
        dest.writeString(price);
    }

    @Override
    public int describeContents () {
        return 0;
    }

    public static final Creator<CourseModel> CREATOR = new Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel (Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray (int size) {
            return new CourseModel[size];
        }
    };
}
