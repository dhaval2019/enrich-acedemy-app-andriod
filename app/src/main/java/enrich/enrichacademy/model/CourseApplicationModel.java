package enrich.enrichacademy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 25/07/17.
 */

public class CourseApplicationModel {

    @SerializedName("id")
    public int Id;
    @SerializedName("courseId")
    public int CourseId;
    @SerializedName("courseName")
    public String CourseName;
    @SerializedName("userId")
    public int UserId;
    @SerializedName("userName")
    public String UserName;
    @SerializedName("userPhoneNumber")
    public String UserPhoneNumber;
    @SerializedName("applicationDate")
    public String ApplicationDate;
    @SerializedName("courseApplicationStatus")
    public int CourseApplicationStatus;

    public int getId () {
        return Id;
    }

    public void setId (int id) {
        Id = id;
    }

    public int getCourseId () {
        return CourseId;
    }

    public void setCourseId (int courseId) {
        CourseId = courseId;
    }

    public String getCourseName () {
        return CourseName;
    }

    public void setCourseName (String courseName) {
        CourseName = courseName;
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

    public String getUserPhoneNumber () {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber (String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getApplicationDate () {
        return ApplicationDate;
    }

    public void setApplicationDate (String applicationDate) {
        ApplicationDate = applicationDate;
    }

    public int getCourseApplicationStatus () {
        return CourseApplicationStatus;
    }

    public void setCourseApplicationStatus (int courseApplicationStatus) {
        CourseApplicationStatus = courseApplicationStatus;
    }
}
