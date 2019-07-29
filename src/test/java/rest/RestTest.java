package rest;

import com.google.gson.Gson;
import helpers.YamlReader;
import io.restassured.response.Response;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * In this class will be tested creation, update and deletion of users, via rest assured
 *
 * @author: dino
 */
public class RestTest {

    private String uri = YamlReader.getProperties().getBaseURL();
    private String operatorUsername = YamlReader.getProperties().getUserName();
    private String operatorPassword = YamlReader.getProperties().getPassword();
    private String sessionToken;
    private Seniorities senioritiesRequests = new Seniorities();
    private Gson gson = new Gson();
    private Technologies technologiesRequests = new Technologies();
    private People people = new People();

    @BeforeMethod public void testPreparation() {
        HelperRest.getTokens(uri, operatorUsername, operatorPassword);
        sessionToken = HelperRest.sessionToken;
    }

    /**
     * Test will check creation of random seniority, technology and person
     *
     * @author: dino
     */
    @Test public void createPersonTest() {
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

        Response createPersonResponse = people
                .createPerson(personBody, uri, sessionToken);
        PersonResponse createPersonObj = gson
                .fromJson(createPersonResponse.asString(),
                        PersonResponse.class);

        assertThat("Person name is the same as we pass in request",
                createPersonObj.getPeopleName(),
                is(personBody.getPeopleName()));

        assertThat("Person seniority id is the same as " + createSeniorityObj
                        .getSeniorityTitle(),
                createPersonObj.getSeniority().getSeniorityId(),
                is(createSeniorityObj.getSeniorityId()));

        assertThat("Person tehcnology id is the same as " + createTechnologyObj
                        .getTechnologyTitle(),
                createPersonObj.getTechnologies().get(0).getTechnologyId(),
                is(createTechnologyObj.getTechnologyId()));

    }

    /**
     * Test will check update functionality of person and it will swap first and last name of all users
     *
     * @author: dino
     */
    @Test public void firstLastNameConversionTest() {
        //We must have at least one user in case that delete user test is run before
        HelperRest.createCompleteRandomPerson();

        //Get all people and store old value tha we will use for comparision after update
        Response getOldPeople = people.getPeople(uri, sessionToken);

        PersonResponse[] getOldPeopleArray = gson
                .fromJson(getOldPeople.asString(), PersonResponse[].class);

        ArrayList<PersonResponse> oldPeopleArrayList = new ArrayList<>(
                Arrays.asList(getOldPeopleArray));

        //Get all people and put it in the list that will be converted
        Response getPeopleResponse = people.getPeople(uri, sessionToken);

        PersonResponse[] getPeopleArray = gson
                .fromJson(getPeopleResponse.asString(), PersonResponse[].class);

        ArrayList<PersonResponse> peopleArrayList = new ArrayList<>(
                Arrays.asList(getPeopleArray));

        //Swap first and last name as string
        ArrayList<String> convertedFullNames = HelperRest
                .convertListOfNames(peopleArrayList);

        //Set converted names to all people arrayList
        for (int i = 0; i < convertedFullNames.size(); i++) {
            peopleArrayList.get(i).setPeopleName(convertedFullNames.get(i));

        }

        //Send update to all and assert that conversion is done
        for (int i = 0; i < peopleArrayList.size(); i++) {
            //Take old first and last name
            String fullNameBeforeConversion = oldPeopleArrayList.get(i)
                    .getPeopleName();
            String firstNameBeforeConversion = fullNameBeforeConversion
                    .substring(0, fullNameBeforeConversion.indexOf(" "));
            String lastNameBeforeConversion = fullNameBeforeConversion
                    .substring(fullNameBeforeConversion.indexOf(" "));

            //Update person
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

            //Check that first and last name are converted
            PersonResponse personAfterConversionObj = gson
                    .fromJson(update.asString(), PersonResponse.class);

            String fullNameAfterConversion = personAfterConversionObj
                    .getPeopleName();
            String firstNameAfterConversion = fullNameAfterConversion
                    .substring(0, fullNameAfterConversion.indexOf(" "));
            String lastNameAfterConversion = fullNameAfterConversion
                    .substring(fullNameAfterConversion.indexOf(" "));

            assertThat("First element in string is now second:",
                    firstNameBeforeConversion.trim(),
                    is(lastNameAfterConversion.trim()));
            assertThat("Second element in string is now first:",
                    lastNameBeforeConversion.trim(),
                    is(firstNameAfterConversion.trim()));

        }
    }

    /**
     * Check will test deletion of all users
     *
     * @author: dino
     */
    @Test public void deleteAllTest() {
        //Create at least one user
        HelperRest.createCompleteRandomPerson();

        //Get all users
        Response getPeopleResponse = people.getPeople(uri, sessionToken);

        PersonResponse[] getPeopleArray = gson
                .fromJson(getPeopleResponse.asString(), PersonResponse[].class);
        ArrayList<PersonResponse> peopleArrayList = new ArrayList<>(
                Arrays.asList(getPeopleArray));
        ArrayList<Integer> getAllPersonIds = new ArrayList<>();

        for (PersonResponse person : peopleArrayList) {
            getAllPersonIds.add(person.getPeopleId());
        }

        for (int i = 0; i <= getAllPersonIds.size() - 1; i++) {
            Response response = people.deletePeople(uri, sessionToken,
                    getAllPersonIds.get(i).toString());
        }

        String emptyPeopleResponse = people.getPeople(uri, sessionToken).body()
                .asString();
        assertThat("There is no users ", emptyPeopleResponse, is("{}"));

    }

}
