package com.rest_api.spring_boot_rest_api.integrationtests.controller.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rest_api.spring_boot_rest_api.config.TestConfigs;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.BookDto;
import com.rest_api.spring_boot_rest_api.integrationtests.testecontainers.AbstractIntegrationTest;
import com.rest_api.spring_boot_rest_api.wrapper.xml.PagedModelBook;
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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerWithXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification requestSpecification;
    private static XmlMapper xmlMapper;

    Date date = new GregorianCalendar(2020, Calendar.JANUARY, 10).getTime();

    private static BookDto book;

    @BeforeAll
    static void setUp(){

        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // disable fail on read hateoas properties.

        book = new BookDto(); // init in Junit lifecycle

        requestSpecification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.HEADER_PARAM_GITHUB)
                .setBasePath("/api/book/v1")
                .setContentType("application/xml")
                .setAccept("application/xml")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // log for request
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // log for response
                .build();
    }

    @Order(1)
    @Test
    void save() throws JsonProcessingException {
        mockBook();


        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

            BookDto createdBook = xmlMapper.readValue(content, BookDto.class);
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

    @Order(2)
    @Test
    void update() throws JsonProcessingException {

        book.setAuthor("repoleved");

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDto createdPerson = xmlMapper.readValue(content, BookDto.class);
        book = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getAuthor());
        assertNotNull(createdPerson.getTitle());
        assertNotNull(createdPerson.getDate());
        assertNotNull(createdPerson.getPrice());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("RestApi", createdPerson.getTitle());
        assertEquals("repoleved", createdPerson.getAuthor());
        assertEquals(67.67F, createdPerson.getPrice());
        assertEquals(date, createdPerson.getDate());
    }

    private void mockBook() {

        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 10).getTime();

        book.setAuthor("Test Man");
        book.setTitle("RestApi");
        book.setPrice(67.67F);
        book.setDate(date);
    }


    @Order(3)
    @Test
    void findById() throws JsonProcessingException {

        var content = given(requestSpecification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", book.getId()) // parameter name in controller /{id}
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDto createdPerson = xmlMapper.readValue(content, BookDto.class);
        book = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getAuthor());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("repoleved", createdPerson.getAuthor());
        assertEquals("RestApi", createdPerson.getTitle());
        assertEquals(67.67F, createdPerson.getPrice());
        assertEquals(date, createdPerson.getDate());
    }

    @Order(4)
    @Test
    void findAll() throws JsonProcessingException {

        var content = given(requestSpecification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .basePath("/api/book/v1/find-all")
                .queryParam("size", 12)
                .queryParam("page", 0)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PagedModelBook wrapperDto = xmlMapper.readValue(content, PagedModelBook.class);
        List<BookDto> books = wrapperDto.getContent();

        BookDto bookOne = books.get(0);

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);

        assertEquals("repoleved", bookOne.getAuthor());
        assertEquals("RestApi", bookOne.getTitle());
        assertEquals(67.67F, bookOne.getPrice());
        assertEquals(date, bookOne.getDate());
    }

    @Order(5)
    @Test
    void delete(){
        given(requestSpecification)
                .pathParam("id", book.getId()) // parameter name in controller /{id}
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}