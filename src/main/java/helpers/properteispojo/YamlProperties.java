package helpers.properteispojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YamlProperties {
    @SerializedName("baseURL")
    @Expose
    private String baseURL;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("seniorityApi")
    @Expose
    private SeniorityApi seniorityApi;
    @SerializedName("techonolgyApi")
    @Expose
    private TechonolgyApi techonolgyApi;
    @SerializedName("peopleApi")
    @Expose
    private PeopleApi peopleApi;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SeniorityApi getSeniorityApi() {
        return seniorityApi;
    }

    public void setSeniorityApi(SeniorityApi seniorityApi) {
        this.seniorityApi = seniorityApi;
    }

    public TechonolgyApi getTechonolgyApi() {
        return techonolgyApi;
    }

    public void setTechonolgyApi(TechonolgyApi techonolgyApi) {
        this.techonolgyApi = techonolgyApi;
    }

    public PeopleApi getPeopleApi() {
        return peopleApi;
    }

    public void setPeopleApi(PeopleApi peopleApi) {
        this.peopleApi = peopleApi;
    }
}
