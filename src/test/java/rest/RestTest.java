package rest;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.body.CreatePersonBody;
import rest.requests.HelperRest;
import rest.requests.People;
import rest.requests.Seniorities;
import rest.requests.Technologies;
import rest.response.PersonResponse;
import rest.response.SeniorityResponse;
import rest.response.TechnologyResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import static org.hamcrest.core.Is.is;

public class RestTest {

    private String uri = "https://qa-sandbox.apps.htec.rs";
    private String operatorUsername = "dinorac87@gmail.com";
    private String operatorPassword = "Test1234!";
    private String sessionToken;
    private Seniorities senioritiesRequests = new Seniorities();
    private Gson gson = new Gson();
    private Technologies technologiesRequests = new Technologies();
    private People people = new People();

    @BeforeMethod public void testPreparation() {
        HelperRest.getTokens(uri, operatorUsername, operatorPassword);
        sessionToken = HelperRest.sessionToken;
    }

    @Test public void CreatePerson() {
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
                is(randomSeniority.get("seniority_title")));

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
                is(randomTechnology.get("technology_title")));

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
                is(personBody.getPeopleName()));

        assertThat("Person sniority id is the same as " + createSeniorityObj
                        .getSeniorityTitle(),
                createPersonObj.getSeniority().getSeniorityId(),
                is(createSeniorityObj.getSeniorityId()));

        assertThat("Person tehcnology id is the same as " + createTechnologyObj
                        .getTechnologyTitle(),
                createPersonObj.getTechnologies().get(0).getTechnologyId(),
                is(createTechnologyObj.getTechnologyId()));

    }

}
