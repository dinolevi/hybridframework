package rest.requests;

import com.google.gson.Gson;
import helpers.YamlReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.LinkedHashMap;

/**
 * Class contains requests for contacting Technologies API
 *
 * @author dino
 */
public class Technologies {

    /**
     * Request will send request for creating technology when all parameters are inserted
     *
     * @param body         prepared body of requests
     * @param uri          base url
     * @param sessionToken session token
     * @return return response which can be used for assertions
     * @author: dino
     */
    public Response createTechnology(LinkedHashMap<String, String> body,
            String uri, String sessionToken) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(body);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath(YamlReader.getProperties().getTechonolgyApi().getCreateTechnology());
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        builder.addHeader("Authorization", "Bearer " + sessionToken);

        RequestSpecification rspec = builder.build();

        return HelperRest.sendPostRequest(rspec);
    }
}
