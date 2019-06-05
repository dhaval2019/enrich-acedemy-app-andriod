package enrich.enrichacademy.model;

/**
 * Created by varunbarve on 25/07/17.
 */

public class OrderRequestModel {

    public int Id;
    public String OrderTitle;
    public int ServiceId;
    public String ServiceName;
    public int UserId;
    public String UserName;
    public String OrderDate;
    public String BookDate;
    public String Price;
    public String Tax;
    public int IsCOD;
    public int ServiceStatus;
    public int CancelStatus;
    public String CancelReason;
    public String TimeSlotStart;
    public int TimeSlotId;

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

    public String getTimeSlotStart () {
        return TimeSlotStart;
    }

    public void setTimeSlotStart (String timeSlotStart) {
        TimeSlotStart = timeSlotStart;
    }

    public int getTimeSlotId () {
        return TimeSlotId;
    }

    public void setTimeSlotId (int timeSlotId) {
        TimeSlotId = timeSlotId;
    }
}
