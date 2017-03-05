package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Admin on 05-Mar-17.
 */

public class OrderModel implements Parcelable {

    @SerializedName("Key")
    public int Id;

    @SerializedName("Service_Id")
    public int ServiceId;

    @SerializedName("BookingDate")
    public Date BookingDate;

    @SerializedName("Amount")
    public double Amount;

    @SerializedName("Status")
    public String BookingStatus;

    @SerializedName("IsCod")
    public boolean IsCod;

    @SerializedName("IsCancelled")
    public boolean IsCancelled;

    @SerializedName("Payment_Id")
    public int PaymentMode;

    public String serviceName;

    public OrderModel() {
    }

    protected OrderModel(Parcel in) {
        Id = in.readInt();
        ServiceId = in.readInt();
        Amount = in.readDouble();
        BookingStatus = in.readString();
        IsCod = in.readByte() != 0;
        IsCancelled = in.readByte() != 0;
        PaymentMode = in.readInt();
        BookingDate = (Date) in.readSerializable();
        serviceName = in.readString();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeInt(ServiceId);
        parcel.writeDouble(Amount);
        parcel.writeString(BookingStatus);
        parcel.writeByte((byte) (IsCod ? 1 : 0));
        parcel.writeByte((byte) (IsCancelled ? 1 : 0));
        parcel.writeInt(PaymentMode);
        parcel.writeSerializable(BookingDate);
        parcel.writeString(serviceName);
    }
}
