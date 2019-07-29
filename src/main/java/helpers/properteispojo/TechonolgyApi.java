package helpers.properteispojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TechonolgyApi {

    @SerializedName("createTechnology")
    @Expose
    private String createTechnology;

    public String getCreateTechnology() {
        return createTechnology;
    }

    public void setCreateTechnology(String createTechnology) {
        this.createTechnology = createTechnology;
    }

}
