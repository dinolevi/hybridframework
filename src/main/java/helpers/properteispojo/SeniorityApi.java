package helpers.properteispojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeniorityApi {
    @SerializedName("createSeniority")
    @Expose
    private String createSeniority;

    public String getCreateSeniority() {
        return createSeniority;
    }

    public void setCreateSeniority(String createSeniority) {
        this.createSeniority = createSeniority;
    }
}
