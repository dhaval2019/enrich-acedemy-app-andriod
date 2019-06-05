package enrich.enrichacademy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 04-Mar-17.
 */

public class TopologyModel {

    @SerializedName("id")
    public int Id;

    @SerializedName("name")
    public String Name;

    @SerializedName("order")
    public int Order;

    public TopologyModel(int id, String name) {
        this.Id = id;
        this.Name = name;
    }
}
