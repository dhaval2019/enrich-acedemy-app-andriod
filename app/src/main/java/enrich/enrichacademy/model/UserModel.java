package enrich.enrichacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Admin on 02-Mar-17.
 */

public class UserModel implements Parcelable {

    @SerializedName("Id")
    public String Id;

    @SerializedName("Mobile")
    public String Mobile;

    @SerializedName("LastOTP")
    public String LastOTP;

    @SerializedName("FirstName")
    public String Name;

    @SerializedName("Gender")
    public String Gender;

    public String DateOfBirth;

    @SerializedName("Email")
    public String EmailAddress;

    @SerializedName("Address")
    public String Address;

    @SerializedName("Location")
    public String Location;

    public String Password;

    public boolean isRegistrationCompleted;

    @SerializedName("ProfileImage")
    public String Image;

    @SerializedName("IsActive")
    public boolean IsActive;

    @SerializedName("Platform")
    public String Platform;

    @SerializedName("CreatedOn")
    public Date CreatedOn;

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        Id = in.readString();
        Mobile = in.readString();
        LastOTP = in.readString();
        Name = in.readString();
        Gender = in.readString();
        DateOfBirth = in.readString();
        EmailAddress = in.readString();
        Address = in.readString();
        Location = in.readString();
        Password = in.readString();
        isRegistrationCompleted = in.readByte() != 0;
        Image = in.readString();
        IsActive = in.readByte() != 0;
        Platform = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLastOTP() {
        return LastOTP;
    }

    public void setLastOTP(String lastOTP) {
        LastOTP = lastOTP;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isRegistrationCompleted() {
        return isRegistrationCompleted;
    }

    public void setRegistrationCompleted(boolean registrationCompleted) {
        isRegistrationCompleted = registrationCompleted;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Mobile);
        parcel.writeString(LastOTP);
        parcel.writeString(Name);
        parcel.writeString(Gender);
        parcel.writeString(DateOfBirth);
        parcel.writeString(EmailAddress);
        parcel.writeString(Address);
        parcel.writeString(Location);
        parcel.writeString(Password);
        parcel.writeByte((byte) (isRegistrationCompleted ? 1 : 0));
        parcel.writeString(Image);
        parcel.writeByte((byte) (IsActive ? 1 : 0));
        parcel.writeString(Platform);
    }
}
