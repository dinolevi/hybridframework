package rest.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreatePersonBody {
    @SerializedName("people_name")
    @Expose
    private String peopleName;
    @SerializedName("seniority_id")
    @Expose
    private Integer seniorityId;
    @SerializedName("technologies")
    @Expose
    private List<Integer> technologies = null;

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public Integer getSeniorityId() {
        return seniorityId;
    }

    public void setSeniorityId(Integer seniorityId) {
        this.seniorityId = seniorityId;
    }

    public List<Integer> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Integer> technologies) {
        this.technologies = technologies;
    }
}
