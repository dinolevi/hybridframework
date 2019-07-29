package rest.requests;

import com.google.gson.Gson;
import helpers.YamlReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.LinkedHashMap;

/**
 * Class contains requests for contacting Seniorities API
 *
 * @author dino
 */
public class Seniorities {

    /**
     * Request will send request for creating seniority when all parameters are inserted
     *
     * @param body         prepared body of requests
     * @param uri          base url
     * @param sessionToken session token
     * @return return response which can be used for assertions
     * @author: dino
     */
    public Response createSeniority(LinkedHashMap<String, String> body,
            String uri, String sessionToken) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(body);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getSeniorityApi().getCreateSeniority());
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPostRequest(rspec);
    }

    //TBD getSeniority and delete
}
