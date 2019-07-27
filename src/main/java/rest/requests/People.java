package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rest.body.CreatePersonBody;

import java.util.LinkedHashMap;

public class People {

    public Response createPerson(CreatePersonBody personBody, String uri, String sessionToken){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(personBody);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/people/person");
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPostRequest(rspec);
    }
}
