package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 22-Feb-17.
 */

public class HistoryModel implements Parcelable {

    public String name;
    public String rate;
    public String time;

    public HistoryModel(String name, String rate, String time) {
        this.name = name;
        this.rate = rate;
        this.time = time;
    }


    protected HistoryModel(Parcel in) {
        name = in.readString();
        rate = in.readString();
        time = in.readString();
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(rate);
        parcel.writeString(time);
    }
}
