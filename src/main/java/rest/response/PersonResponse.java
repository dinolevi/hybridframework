package rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PersonResponse {
    @SerializedName("people_id") @Expose private Integer peopleId;
    @SerializedName("people_name") @Expose private String peopleName;
    @SerializedName("seniority") @Expose private SeniorityResponse seniority;
    @SerializedName("technologies") @Expose private ArrayList<TechnologyResponse> technologies = new ArrayList<>();
    @SerializedName("role") @Expose private Object role;

    public Integer getPeopleId() {
        return peopleId;
    }
    public SeniorityResponse getSeniority() {
        return seniority;
    }
    public void setSeniority(SeniorityResponse seniority) {
        this.seniority = seniority;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public ArrayList<TechnologyResponse> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<TechnologyResponse> technologies) {
        this.technologies = technologies;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

}

