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
    public String rate;
    public String[] timings;
    @SerializedName("Description")
    public String description;
    public CategoryModel categoryModel;

    @SerializedName("Category_Id")
    public int CategoryId;

    public int selectedTimeSlotId;

    protected ServicesModel(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        rate = in.readString();
        timings = in.createStringArray();
        description = in.readString();
        categoryModel = in.readParcelable(CategoryModel.class.getClassLoader());
        CategoryId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(name);
        dest.writeString(rate);
        dest.writeStringArray(timings);
        dest.writeString(description);
        dest.writeParcelable(categoryModel, flags);
        dest.writeInt(CategoryId);
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
