package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 03-Mar-17.
 */

public class CategoryModel implements Parcelable {

    public int Id;
    public String Name;
    public String Description;
    public boolean IsActive;


    public CategoryModel(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        Description = in.readString();
        IsActive = in.readByte() != 0;
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    public CategoryModel(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Name);
        parcel.writeString(Description);
        parcel.writeByte((byte) (IsActive ? 1 : 0));
    }
}
