package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.LinkedHashMap;

public class Seniorities {

    public Response createSeniority(LinkedHashMap<String, String> body, String uri, String sessionToken){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(body);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/seniorities/seniority");
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPostRequest(rspec);
    }

    //TBD getSeniority and delete
}