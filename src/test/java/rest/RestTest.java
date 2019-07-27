package rest;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.*;

import javafx.scene.layout.Priority;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.body.CreatePersonBody;
import rest.body.UpdatePerson;
import rest.requests.HelperRest;
import rest.requests.People;
import rest.requests.Seniorities;
import rest.requests.Technologies;
import rest.response.PersonResponse;
import rest.response.SeniorityResponse;
import rest.response.TechnologyResponse;

import java.util.*;

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

    @Test(priority = 1) public void CreatePerson() {
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

    @Test(priority = 2) public void firstLastNameConversion() {
        //Get all people and put it in the list
        Response getPeopleResponse = people.getPeople(uri, sessionToken);

        PersonResponse[] getPeopleArray = gson
                .fromJson(getPeopleResponse.asString(), PersonResponse[].class);

        ArrayList<PersonResponse> peopleArrayList = new ArrayList<>(
                Arrays.asList(getPeopleArray));

        for (PersonResponse person : peopleArrayList){
            System.out.println("\nBEFORE CONVERTION"+ person.getPeopleName());
        }

        //Swap first and last name
        ArrayList<String> convertedFullNames = HelperRest
                .converteListOfNames(peopleArrayList);

        for(int i = 0; i<convertedFullNames.size(); i++){
            System.out.println("\nAFTER CONVERSION" + convertedFullNames.get(i));
        }

        //Set converted names to all people
        for (int i = 0; i < convertedFullNames.size(); i++) {
            peopleArrayList.get(i).setPeopleName(convertedFullNames.get(i));

        }

        //Send update to all
        for (int i = 0; i < peopleArrayList.size(); i++) {
            UpdatePerson updatePersonBody = new UpdatePerson();
            updatePersonBody
                    .setPeopleName(peopleArrayList.get(i).getPeopleName());
            updatePersonBody.setSeniorityId(
                    peopleArrayList.get(i).getSeniority().getSeniorityId());

            updatePersonBody.getTechnologies()
                    .add(peopleArrayList.get(i).getTechnologies().get(0)
                            .getTechnologyId());

            Response update = people
                    .updatePerson(updatePersonBody, uri, sessionToken,
                            peopleArrayList.get(i).getPeopleId().toString());

        }
    }

//    @Test(priority = 3) public void deleteAll() {
//        Response getPeopleResponse = people.getPeople(uri, sessionToken);
//
//        PersonResponse[] getPeopleArray = gson
//                .fromJson(getPeopleResponse.asString(), PersonResponse[].class);
//        ArrayList<PersonResponse> peopleArrayList = new ArrayList<>(
//                Arrays.asList(getPeopleArray));
//        ArrayList<Integer> getAllPersonIds = new ArrayList<>();
//
//        for (PersonResponse person : peopleArrayList) {
//            getAllPersonIds.add(person.getPeopleId());
//        }
//
//        for (int i = 0; i <= getAllPersonIds.size() - 1; i++) {
//            Response response = people.deletePeople(uri, sessionToken,
//                    getAllPersonIds.get(i).toString());
//        }
//
//        String emptyPeopleResponse = people.getPeople(uri, sessionToken).body()
//                .asString();
//        assertThat("There is no users ", emptyPeopleResponse, is("{}"));
//
//    }

    @Test public void converterTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("RAMBO MAMBO");
        list.add("CAKA NOSRIS");
        list.add("PERA ZDERA");

        ArrayList<String> converted = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            converted.add(HelperRest.convertName(list.get(i)));
        }

        for (String i : converted){
            System.out.println(i);
        }
    }

}
