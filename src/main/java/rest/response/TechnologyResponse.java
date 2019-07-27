package rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TechnologyResponse {
    @SerializedName("technology_title")
    @Expose
    private String technologyTitle;
    @SerializedName("technology_id")
    @Expose
    private Integer technologyId;

    public String getTechnologyTitle() {
        return technologyTitle;
    }

    public void setTechnologyTitle(String technologyTitle) {
        this.technologyTitle = technologyTitle;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }
}
