package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.*;

import rest.response.LoginResponse;
import rest.response.PersonResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    public static Response sendPostRequest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .post().then().log().all().extract().response();
    }

    public static Response sendGetReguest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .get().then().log().all().extract().response();
    }

    public static Response sendPut(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .put().then().log().all().extract().response();
    }

    public static Response sendDeleteRequest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .delete().then().log().all().extract().response();
    }

//    public static String convertName(String name) {
//        String firstName = name.substring(0, name.indexOf(" "));
//        String lastName = name.substring(name.indexOf(" "));
//        String cName = lastName + " " + firstName;
//        return cName;
//    }

    public static String convertName(String name) {
        String[] words = name.split(" ");
        String first = words [0];
        String lastName = words [1];
        String cName = lastName + " " + first;
        return cName;
    }

    public static ArrayList<String> converteListOfNames(
            ArrayList<PersonResponse> peopleArrayList) {

        ArrayList<String> listFullName = new ArrayList<>();


        for (int i=0; i < peopleArrayList.size(); i++) {

            listFullName.add(peopleArrayList.get(i).getPeopleName());
        }


        ArrayList<String> convertedList = new ArrayList<>();
        for (int i = 0; i < listFullName.size(); i++) {

              convertedList.add(HelperRest.reverseName(listFullName.get(i)));

        }

        return convertedList;
    }

    public static String reverseName (String name) {

        name = name.trim();

        StringBuilder reversedNameBuilder = new StringBuilder();
        StringBuilder subNameBuilder = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {

            char currentChar = name.charAt(i);

            if (currentChar != ' ' && currentChar != '-') {
                subNameBuilder.append(currentChar);
            } else {
                reversedNameBuilder.insert(0, currentChar + subNameBuilder.toString());
                subNameBuilder.setLength(0);
            }

        }

        return reversedNameBuilder.insert(0, subNameBuilder.toString()).toString();

    }

}


