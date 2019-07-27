package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.*;

import rest.response.LoginResponse;

import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class HelperRest {

    public static String sessionToken;
    public static String refreshToken;

    public static void getTokens(String uri, String operatorUsername,
            String operatorPassword) {
        //Prepare body and convert to json
        //Note: don't create oneLiner maps there is bug in gson and it will return null when create it
        LinkedHashMap<String, String> loginBody = new LinkedHashMap<String, String>();
        loginBody.put("email", operatorUsername);
        loginBody.put("password", operatorPassword);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(loginBody);

        //Prepare request
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(uri);
        builder.setBasePath("/api/users/login");
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        RequestSpecification rspec = builder.build();

        //Send request
        Response response = sendPostRequest(rspec);

        //Convert response and and extract sessionToken and refreshToken
        LoginResponse responseObject = gson
                .fromJson(response.asString(), LoginResponse.class);

        assertThat("success is true", responseObject.getSuccess(), is(true));
        sessionToken = responseObject.getToken();
        refreshToken = responseObject.getRefreshToken();

    }

    public static Response sendPostRequest(RequestSpecification rspec){
       return given().spec(rspec).log().all()
                .relaxedHTTPSValidation().when().post().then().log().all()
                .extract().response();
    }
}


