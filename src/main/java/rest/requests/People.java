package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rest.body.CreatePersonBody;
import rest.body.UpdatePerson;

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

    public Response getPeople(String uri, String sessionToken){
        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/people/all");
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        //sendRequest
        RequestSpecification rspec = builder.build();

        return HelperRest.sendGetReguest(rspec);
    }

    public Response updatePerson(UpdatePerson updatePerson,String uri, String sessionToken, String personId){
        Gson gson = new Gson();
        String jsonBody = gson.toJson(updatePerson);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(" /api/people/person/"+personId);
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPut(rspec);
    }

    public Response deletePeople(String uri, String sessionToken, String personId){
        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/people/person/"+personId);
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        //sendRequest
        RequestSpecification rspec = builder.build();

        return HelperRest.sendDeleteRequest(rspec);
    }

    public Response getPersonDetails(String uri, String sessionToken, String personId){
               //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/people/person/" + personId);
        builder.setContentType("application/json");

        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendGetReguest(rspec);
    }

}
