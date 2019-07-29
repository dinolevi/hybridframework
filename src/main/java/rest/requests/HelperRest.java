package rest.requests;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.core.Is;
import rest.body.CreatePersonBody;
import rest.response.LoginResponse;
import rest.response.PersonResponse;
import rest.response.SeniorityResponse;
import rest.response.TechnologyResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Class provide helpers for rest testing suche as getToken, createRandomPerson,
 * send get, post, put, delete requests
 *
 * @author: dino
 */
public class HelperRest {

    public static String sessionToken;
    public static String refreshToken;

    /**
     * Method will generate session and refresh tokens and store it to sessionToken
     * and refreshToken attributes when all parameters are inserted
     *
     * @param uri              base url
     * @param operatorUsername operator user name
     * @param operatorPassword operator password
     * @author: dino
     */
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

    /**
     * Generic method for sending Post request when request specification object
     * is inserted as parameter
     *
     * @param rspec request specification object
     * @return response which can be used for assertation
     * @author: dino
     */
    public static Response sendPostRequest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .post().then().log().all().extract().response();
    }

    /**
     * Generic method for sending Get request when request specification object
     * is inserted as parameter
     *
     * @param rspec request specification object
     * @return response which can be used for assertation
     * @author: dino
     */
    public static Response sendGetReguest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .get().then().log().all().extract().response();
    }

    /**
     * Generic method for sending Put request when request specification object
     * is inserted as parameter
     *
     * @param rspec request specification object
     * @return response which can be used for assertation
     * @author: dino
     */
    public static Response sendPut(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .put().then().log().all().extract().response();
    }

    /**
     * Generic method for sending Delete request when request specification object
     * is inserted as parameter
     *
     * @param rspec request specification object
     * @return response which can be used for assertation
     * @author: dino
     */
    public static Response sendDeleteRequest(RequestSpecification rspec) {
        return given().spec(rspec).log().all().relaxedHTTPSValidation().when()
                .delete().then().log().all().extract().response();
    }

    /**
     * Helper function for going through list of all names and switching first and
     * last name position
     *
     * @param peopleArrayList arraylist of people which we want to switch first and last name
     * @return converted list of names as String
     * @author: dino
     */
    public static ArrayList<String> convertListOfNames(
            ArrayList<PersonResponse> peopleArrayList) {

        //Extract full name from People array list
        ArrayList<String> listFullName = new ArrayList<>();

        for (int i = 0; i < peopleArrayList.size(); i++) {

            listFullName.add(peopleArrayList.get(i).getPeopleName());
        }

        //For loop for switching first and last name
        ArrayList<String> convertedList = new ArrayList<>();
        for (int i = 0; i < listFullName.size(); i++) {

            convertedList.add(HelperRest.switchName(listFullName.get(i)));

        }

        return convertedList;
    }

    /**
     * Method will create random person with added random seniority and technology

     * @return random created person
     * @author: dino
     */
    public static PersonResponse createCompleteRandomPerson() {

        String uri = "https://qa-sandbox.apps.htec.rs";
        String sessionToken = HelperRest.sessionToken;
        Seniorities senioritiesRequests = new Seniorities();
        Gson gson = new Gson();
        Technologies technologiesRequests = new Technologies();
        People people = new People();
        //Create random seniority
        LinkedHashMap<String, String> randomSeniority = new LinkedHashMap<String, String>();
        randomSeniority
                .put("seniority_title", "Junior" + new Random().nextInt(9999));

        Response createSeniorityResponse = senioritiesRequests
                .createSeniority(randomSeniority, uri, sessionToken);
        SeniorityResponse createSeniorityObj = gson
                .fromJson(createSeniorityResponse.asString(),
                        SeniorityResponse.class);

        assertThat("Seniority title in response is the same as we sent it ",
                createSeniorityObj.getSeniorityTitle(),
                Is.is(randomSeniority.get("seniority_title")));

        //Create random technology
        LinkedHashMap<String, String> randomTechnology = new LinkedHashMap<String, String>();
        randomTechnology
                .put("technology_title", "Java" + new Random().nextInt(9999));

        Response createTechnologyResponse = technologiesRequests
                .createTechnology(randomTechnology, uri, sessionToken);

        TechnologyResponse createTechnologyObj = gson
                .fromJson(createTechnologyResponse.asString(),
                        TechnologyResponse.class);

        assertThat("Seniority title in response is the same as we sent it ",
                createTechnologyObj.getTechnologyTitle(),
                Is.is(randomTechnology.get("technology_title")));

        //Create random Person
        CreatePersonBody personBody = new CreatePersonBody();
        personBody.setPeopleName("Pera Peric" + new Random().nextInt(9999));
        personBody.setSeniorityId(createSeniorityObj.getSeniorityId());
        ArrayList<Integer> technologyList = new ArrayList<>();
        technologyList.add(createTechnologyObj.getTechnologyId().intValue());
        personBody.setTechnologies(technologyList);

        Response crestePersonResponse = people
                .createPerson(personBody, uri, sessionToken);
        PersonResponse createPersonObj = gson
                .fromJson(crestePersonResponse.asString(),
                        PersonResponse.class);

        assertThat("Person name is the same as we pass in request",
                createPersonObj.getPeopleName(),
                Is.is(personBody.getPeopleName()));

        assertThat("Person seniority id is the same as " + createSeniorityObj
                        .getSeniorityTitle(),
                createPersonObj.getSeniority().getSeniorityId(),
                Is.is(createSeniorityObj.getSeniorityId()));

        assertThat("Person tehcnology id is the same as " + createTechnologyObj
                        .getTechnologyTitle(),
                createPersonObj.getTechnologies().get(0).getTechnologyId(),
                Is.is(createTechnologyObj.getTechnologyId()));

        return createPersonObj;

    }

    /**
     * Method will switch places of first and last name if they are inserted as one string
     *
     * @param name full name of person
     * @return string with first and last name switched position
     */
    public static String switchName(String name){
        String firstName = name
                .substring(0, name.indexOf(" "));
        String lastName = name
                .substring(name.indexOf(" "));
        return lastName.trim()+ " " + firstName.trim();
    }

}


