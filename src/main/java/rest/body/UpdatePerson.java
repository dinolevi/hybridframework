package rest.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpdatePerson {
    @SerializedName("people_name")
    @Expose
    private String peopleName;
    @SerializedName("seniority_id")
    @Expose
    private Integer seniorityId;



    @SerializedName("technologies")
    @Expose
    private ArrayList<Integer> technologies = new ArrayList<>();

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

    public ArrayList<Integer> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<Integer> technologies) {
        this.technologies = technologies;
    }
}
