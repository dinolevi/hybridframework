package rest.requests;

import com.google.gson.Gson;
import helpers.YamlReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rest.body.CreatePersonBody;
import rest.body.UpdatePerson;

/**
 * Class contains requests for contacting People API
 *
 * @author dino
 */
public class People {

    /**
     * Request will send request for creating person when all parameters are inserted
     *
     * @param personBody   prepared body of requests
     * @param uri          base url
     * @param sessionToken session token
     * @return return response which can be used for assertions
     * @author: dino
     */
    public Response createPerson(CreatePersonBody personBody, String uri,
            String sessionToken) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(personBody);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getPeopleApi().getCreatePerson());
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPostRequest(rspec);
    }

    /**
     * Request which will return details for all people
     *
     * @param uri          base url
     * @param sessionToken session token
     * @return return details for all users
     * @author: dino
     */
    public Response getPeople(String uri, String sessionToken) {
        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getPeopleApi().getGetPeople());
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        //sendRequest
        RequestSpecification rspec = builder.build();

        return HelperRest.sendGetReguest(rspec);
    }

    /**
     * Request which will update person when all parameters are inserted
     *
     * @param updatePerson body
     * @param uri          base url
     * @param sessionToken session token
     * @param personId     person id
     * @return response after updated which can be used for assertions
     * @author: dino
     */
    public Response updatePerson(UpdatePerson updatePerson, String uri,
            String sessionToken, String personId) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(updatePerson);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getPeopleApi().getUpdatePerson() + personId);
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPut(rspec);
    }

    /**
     * Request which will delete person when all parameters are inserted
     *
     * @param uri          base url
     * @param sessionToken session token
     * @param personId     personId
     * @return response for successful deletion of user
     * @author: dino
     */
    public Response deletePeople(String uri, String sessionToken,
            String personId) {
        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getPeopleApi().getDeletePeople() + personId);
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        //sendRequest
        RequestSpecification rspec = builder.build();

        return HelperRest.sendDeleteRequest(rspec);
    }

    /**
     * Request which will return details for specific person
     *
     * @param uri          base url
     * @param sessionToken session token
     * @return return details for specific person
     * @author: dino
     */
    public Response getPersonDetails(String uri, String sessionToken,
            String personId) {
        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getPeopleApi().getGetPersonDetails() + personId);
        builder.setContentType("application/json");

        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendGetReguest(rspec);
    }

}
