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

    public String duration;

    @SerializedName("Description")
    public String description;

    @SerializedName("Category")
    public CategoryModel categoryModel;

    @SerializedName("Category_Id")
    public int CategoryId;

    protected CourseModel(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        rate = in.readString();
        duration = in.readString();
        description = in.readString();
        categoryModel = in.readParcelable(CategoryModel.class.getClassLoader());
        CategoryId = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(name);
        parcel.writeString(rate);
        parcel.writeString(duration);
        parcel.writeString(description);
        parcel.writeParcelable(categoryModel, i);
        parcel.writeInt(CategoryId);
    }
}
