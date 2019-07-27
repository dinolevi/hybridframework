package rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeniorityResponse {
    @SerializedName("seniority_title")
    @Expose
    private String seniorityTitle;
    @SerializedName("seniority_id")
    @Expose
    private Integer seniorityId;

    public String getSeniorityTitle() {
        return seniorityTitle;
    }

    public void setSeniorityTitle(String seniorityTitle) {
        this.seniorityTitle = seniorityTitle;
    }

    public Integer getSeniorityId() {
        return seniorityId;
    }

    public void setSeniorityId(Integer seniorityId) {
        this.seniorityId = seniorityId;
    }
}
