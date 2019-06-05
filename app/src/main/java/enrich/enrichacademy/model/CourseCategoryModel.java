package enrich.enrichacademy.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 24/07/17.
 */

public class CourseCategoryModel implements Parcelable {

    @SerializedName("id")
    public int Id;
    @SerializedName("name")
    public String Name;
    @SerializedName("order")
    public int Order;
    @SerializedName("createdOn")
    public String CreatedOn;
    @SerializedName("isActive")
    public int IsActive;

    public CourseCategoryModel(int id, String name) {
        Id = id;
        Name = name;
    }

    protected CourseCategoryModel(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        Order = in.readInt();
        CreatedOn = in.readString();
        IsActive = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeInt(Order);
        dest.writeString(CreatedOn);
        dest.writeInt(IsActive);
    }

    public static final Creator<CourseCategoryModel> CREATOR = new Creator<CourseCategoryModel>() {
        @Override
        public CourseCategoryModel createFromParcel(Parcel in) {
            return new CourseCategoryModel(in);
        }

        @Override
        public CourseCategoryModel[] newArray(int size) {
            return new CourseCategoryModel[size];
        }
    };
}
