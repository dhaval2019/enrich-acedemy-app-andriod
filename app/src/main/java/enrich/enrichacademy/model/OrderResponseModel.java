package enrich.enrichacademy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 25/07/17.
 */

public class OrderResponseModel {

    @SerializedName("id")
    public int Id;
    @SerializedName("orderTitle")
    public String OrderTitle;
    @SerializedName("serviceId")
    public int ServiceId;
    @SerializedName("serviceName")
    public String ServiceName;
    @SerializedName("userId")
    public int UserId;
    @SerializedName("userName")
    public String UserName;
    @SerializedName("orderDate")
    public String OrderDate;
    @SerializedName("bookDate")
    public String BookDate;
    @SerializedName("price")
    public String Price;
    @SerializedName("tax")
    public String Tax;
    @SerializedName("isCOD")
    public int IsCOD;
    @SerializedName("serviceStatus")
    public int ServiceStatus;
    @SerializedName("cancelStatus")
    public int CancelStatus;
    @SerializedName("cancelReason")
    public String CancelReason;

    public int getId () {
        return Id;
    }

    public void setId (int id) {
        Id = id;
    }

    public String getOrderTitle () {
        return OrderTitle;
    }

    public void setOrderTitle (String orderTitle) {
        OrderTitle = orderTitle;
    }

    public int getServiceId () {
        return ServiceId;
    }

    public void setServiceId (int serviceId) {
        ServiceId = serviceId;
    }

    public String getServiceName () {
        return ServiceName;
    }

    public void setServiceName (String serviceName) {
        ServiceName = serviceName;
    }

    public int getUserId () {
        return UserId;
    }

    public void setUserId (int userId) {
        UserId = userId;
    }

    public String getUserName () {
        return UserName;
    }

    public void setUserName (String userName) {
        UserName = userName;
    }

    public String getOrderDate () {
        return OrderDate;
    }

    public void setOrderDate (String orderDate) {
        OrderDate = orderDate;
    }

    public String getBookDate () {
        return BookDate;
    }

    public void setBookDate (String bookDate) {
        BookDate = bookDate;
    }

    public String getPrice () {
        return Price;
    }

    public void setPrice (String price) {
        Price = price;
    }

    public String getTax () {
        return Tax;
    }

    public void setTax (String tax) {
        Tax = tax;
    }

    public int getIsCOD () {
        return IsCOD;
    }

    public void setIsCOD (int isCOD) {
        IsCOD = isCOD;
    }

    public int getServiceStatus () {
        return ServiceStatus;
    }

    public void setServiceStatus (int serviceStatus) {
        ServiceStatus = serviceStatus;
    }

    public int getCancelStatus () {
        return CancelStatus;
    }

    public void setCancelStatus (int cancelStatus) {
        CancelStatus = cancelStatus;
    }

    public String getCancelReason () {
        return CancelReason;
    }

    public void setCancelReason (String cancelReason) {
        CancelReason = cancelReason;
    }
}
