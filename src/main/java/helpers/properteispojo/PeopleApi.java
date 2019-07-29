package helpers.properteispojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeopleApi {
    @SerializedName("createPerson")
    @Expose
    private String createPerson;
    @SerializedName("getPeople")
    @Expose
    private String getPeople;
    @SerializedName("updatePerson")
    @Expose
    private String updatePerson;
    @SerializedName("deletePeople")
    @Expose
    private String deletePeople;
    @SerializedName("getPersonDetails")
    @Expose
    private String getPersonDetails;

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getGetPeople() {
        return getPeople;
    }

    public void setGetPeople(String getPeople) {
        this.getPeople = getPeople;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getDeletePeople() {
        return deletePeople;
    }

    public void setDeletePeople(String deletePeople) {
        this.deletePeople = deletePeople;
    }

    public String getGetPersonDetails() {
        return getPersonDetails;
    }

    public void setGetPersonDetails(String getPersonDetails) {
        this.getPersonDetails = getPersonDetails;
    }
}
