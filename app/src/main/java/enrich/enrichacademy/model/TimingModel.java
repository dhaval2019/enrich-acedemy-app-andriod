package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Admin on 05-Mar-17.
 */

public class TimingModel implements Parcelable {

    public int Id;
    public int Type_Id;
    public Date FromDate;
    public Date ToDate;
    public String Timings;
    public String Duration;
    public String Description;

    protected TimingModel(Parcel in) {
        Id = in.readInt();
        Type_Id = in.readInt();
        Timings = in.readString();
        Duration = in.readString();
        Description = in.readString();
    }

    public static final Creator<TimingModel> CREATOR = new Creator<TimingModel>() {
        @Override
        public TimingModel createFromParcel(Parcel in) {
            return new TimingModel(in);
        }

        @Override
        public TimingModel[] newArray(int size) {
            return new TimingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeInt(Type_Id);
        parcel.writeString(Timings);
        parcel.writeString(Duration);
        parcel.writeString(Description);
    }
}
