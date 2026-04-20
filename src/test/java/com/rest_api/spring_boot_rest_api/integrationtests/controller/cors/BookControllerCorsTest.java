package com.rest_api.spring_boot_rest_api.integrationtests.controller.cors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest_api.spring_boot_rest_api.config.TestConfigs;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.BookDto;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.PersonDto;
import com.rest_api.spring_boot_rest_api.integrationtests.testecontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification;
    private static ObjectMapper objectMapper;

    Date date = new GregorianCalendar(2020, Calendar.JANUARY, 10).getTime();

    private static BookDto book;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // disable fail on read hateoas properties.

        book = new BookDto(); // init in Junit lifecycle
    }

    @Order(1)
    @Test
    void save() throws JsonProcessingException {
        mockPerson();

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_GITHUB)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

            BookDto createdBook = objectMapper.readValue(content, BookDto.class);
            book = createdBook;

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getAuthor());
        assertNotNull(createdBook.getTitle());
        assertNotNull(createdBook.getPrice());
        assertNotNull(createdBook.getDate());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Test Man", createdBook.getAuthor());
        assertEquals("RestApi", createdBook.getTitle());
        assertEquals(67.67F, createdBook.getPrice());
        assertEquals(date, createdBook.getDate());
    }

    @Order(2)
    @Test
    void saveWithWrongOrigin() throws JsonProcessingException {

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_GOOGLE)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {

        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 10).getTime();

        book.setAuthor("Test Man");
        book.setTitle("RestApi");
        book.setPrice(67.67F);
        book.setDate(date);
    }


    @Order(3)
    @Test
    void findById() throws JsonProcessingException {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_LOCALHOST)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId()) // parameter name in controller /{id}
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookDto createdBook = objectMapper.readValue(content, BookDto.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getAuthor());
        assertNotNull(createdBook.getDate());
        assertNotNull(createdBook.getPrice());
        assertNotNull(createdBook.getTitle());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Test Man", createdBook.getAuthor());
        assertEquals("RestApi", createdBook.getTitle());
        assertEquals(67.67F, createdBook.getPrice());
        assertEquals(date, createdBook.getDate());
    }

    @Order(4)
    @Test
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_GOOGLE)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId()) // parameter name in controller /{id}
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);

    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}