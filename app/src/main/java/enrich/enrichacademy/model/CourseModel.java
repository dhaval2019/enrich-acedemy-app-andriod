package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CourseModel implements Parcelable {

    @SerializedName("Id")
    public int Id;

    @SerializedName("Name")
    public String name;

    @SerializedName("Rate")
    public String rate;

    @SerializedName("Duration")
    public String duration;

    @SerializedName("Description")
    public String description;

    @SerializedName("Category_Id")
    public int CategoryId;

    @SerializedName("BannerImage")
    public String BannerImage;

    @SerializedName("ThumbImage")
    public String ThumbImage;

    @SerializedName("Goals")
    public String Goals;

    @SerializedName("Level")
    public String Level;

    @SerializedName("Meterials")
    public String Meterials;

    @SerializedName("Prerequisites")
    public String Prerequisites;

    @SerializedName("ConnectedCourses")
    public String ConnectedCourses;


    protected CourseModel(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        rate = in.readString();
        duration = in.readString();
        description = in.readString();
        CategoryId = in.readInt();
        BannerImage = in.readString();
        ThumbImage = in.readString();
        Goals = in.readString();
        Level = in.readString();
        Meterials = in.readString();
        Prerequisites = in.readString();
        ConnectedCourses = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(name);
        dest.writeString(rate);
        dest.writeString(duration);
        dest.writeString(description);
        dest.writeInt(CategoryId);
        dest.writeString(BannerImage);
        dest.writeString(ThumbImage);
        dest.writeString(Goals);
        dest.writeString(Level);
        dest.writeString(Meterials);
        dest.writeString(Prerequisites);
        dest.writeString(ConnectedCourses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseModel> CREATOR = new Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };
}
