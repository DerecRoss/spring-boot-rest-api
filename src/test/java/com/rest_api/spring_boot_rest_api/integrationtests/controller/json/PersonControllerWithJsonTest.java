package com.rest_api.spring_boot_rest_api.integrationtests.controller.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest_api.spring_boot_rest_api.config.TestConfigs;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.PersonDto;
import com.rest_api.spring_boot_rest_api.integrationtests.testecontainers.AbstractIntegrationTest;
import com.rest_api.spring_boot_rest_api.wrapper.PersonWrapperDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerWithJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;

    private static PersonDto person;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // disable fail on read hateoas properties.

        person = new PersonDto(); // init in Junit lifecycle

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_GITHUB)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();
    }

    @Order(1)
    @Test
    void save() throws JsonProcessingException {
        mockPerson();


        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

            PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
            person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAdress());
        assertNotNull(createdPerson.getGender());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Test Man", createdPerson.getFirstName());
        assertEquals("Developer", createdPerson.getLastName());
        assertEquals("Linux", createdPerson.getAdress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Order(2)
    @Test
    void update() throws JsonProcessingException {

        person.setLastName("repoleved");

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAdress());
        assertNotNull(createdPerson.getGender());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Test Man", createdPerson.getFirstName());
        assertEquals("repoleved", createdPerson.getLastName());
        assertEquals("Linux", createdPerson.getAdress());
        assertEquals("Male", createdPerson.getGender());
    }

    private void mockPerson() {
        person.setFirstName("Test Man");
        person.setLastName("Developer");
        person.setAdress("Linux");
        person.setGender("Male");
    }


    @Order(3)
    @Test
    void findById() throws JsonProcessingException {

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId()) // parameter name in controller /{id}
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getEnabled());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Test Man", createdPerson.getFirstName());
        assertEquals("repoleved", createdPerson.getLastName());
        assertEquals("Linux", createdPerson.getAdress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Order(4)
    @Test
    void disablePerson() throws JsonProcessingException {

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId()) // parameter name in controller /{id}
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getEnabled());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Test Man", createdPerson.getFirstName());
        assertEquals("repoleved", createdPerson.getLastName());
        assertEquals("Linux", createdPerson.getAdress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Order(5)
    @Test
    void findAll() throws JsonProcessingException {

        var content = given(requestSpecification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .basePath("/api/person/v1/find-all")
                .queryParam("size", 12)
                .queryParam("page", 0)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonWrapperDto wrapperDto = objectMapper.readValue(content, PersonWrapperDto.class);
        List<PersonDto> people = wrapperDto.getPersonEmbeddedDto().getPersonDto();

        PersonDto personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Test Man", personOne.getFirstName());
        assertEquals("repoleved", personOne.getLastName());
        assertEquals("Linux", personOne.getAdress());
        assertEquals("Male", personOne.getGender());
    }

    @Order(6)
    @Test
    void findByName() throws JsonProcessingException {

//        {{baseUrl}}/api/person/v1/findByName/tes?page=0&size=12&direction=asc

        var content = given(requestSpecification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .basePath("/api/person/v1/findByName/{firstName}")
                .pathParam("firstName", "te")
                .queryParam("size", 12)
                .queryParam("page", 0)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonWrapperDto wrapperDto = objectMapper.readValue(content, PersonWrapperDto.class);
        List<PersonDto> people = wrapperDto.getPersonEmbeddedDto().getPersonDto();

        PersonDto personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Test Man", personOne.getFirstName());
        assertEquals("repoleved", personOne.getLastName());
        assertEquals("Linux", personOne.getAdress());
        assertEquals("Male", personOne.getGender());
    }

    @Order(7)
    @Test
    void delete(){
        given(requestSpecification)
                .pathParam("id", person.getId()) // parameter name in controller /{id}
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}