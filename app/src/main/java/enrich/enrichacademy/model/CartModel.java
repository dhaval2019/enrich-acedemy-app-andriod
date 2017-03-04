package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 22-Feb-17.
 */

public class CartModel implements Parcelable {

    public String name;
    public String rate;

    public CartModel(String name, String rate) {
        this.name = name;
        this.rate = rate;
    }

    protected CartModel(Parcel in) {
        name = in.readString();
        rate = in.readString();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
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
    }
}
