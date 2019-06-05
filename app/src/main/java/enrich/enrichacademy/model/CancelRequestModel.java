package enrich.enrichacademy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by varunbarve on 11/08/17.
 */

public class CancelRequestModel {

    @SerializedName("id")
    private int Id;
    @SerializedName("reason")
    private String Reason;

    public int getId () {
        return Id;
    }

    public void setId (int id) {
        Id = id;
    }

    public String getReason () {
        return Reason;
    }

    public void setReason (String reason) {
        Reason = reason;
    }
}
