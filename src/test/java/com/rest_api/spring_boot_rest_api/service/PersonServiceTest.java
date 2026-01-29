package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.exception.RequiredObjectIsNonNullException;
import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.repository.PersonRepository;
import com.rest_api.spring_boot_rest_api.unittests.mapper.mocks.MockPerson;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input; // class for instance and mapper dto and entity

    @Mock // annotation for mockito identifier this class with mock
    PersonRepository personRepository;

    @InjectMocks // this annotation inform mockito for inject mocks in class for tests ( original class )
    private PersonService personService;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this); // open mockito mocks and inject this in instance of PersonService
    }

    @Test
    void testCreateWithNonNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNonNullException.class,
                () -> {
                    personService.save(null);
                });

        String expectedMessage = "Its is now allowed persist null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNonNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNonNullException.class,
                () -> {
                    personService.update(null);
                });

        String expectedMessage = "Its is now allowed persist null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void save() throws BadRequestException {
        Person person = input.mockEntity(1); // mock new entity
        Person personInDb = person;
        personInDb.setId(1L);

        PersonDto personDto = input.mockDTO(1);

        when(personRepository.save(person)).thenReturn(personInDb); // mockito actions when instance use findById(id);

        var result = personService.save(personDto); // this var is for assertions check the result of operation

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks()); // get for check if Hateoas is not null

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1/find-all")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("save") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("POST"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("PUT"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("DELETE"));

        assertEquals("Address Test1", result.getAdress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void update() throws BadRequestException {
        Person person = input.mockEntity(1); // mock new entity
        Person personInDb = person;
        personInDb.setId(1L);

        PersonDto personDto = input.mockDTO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(personInDb); // mockito actions when instance use findById(id);

        var result = personService.update(personDto); // this var is for assertions check the result of operation

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks()); // get for check if Hateoas is not null

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1/find-all")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("save") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("POST"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("PUT"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("DELETE"));

        assertEquals("Address Test1", result.getAdress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() throws BadRequestException {
        Person person = input.mockEntity(1); // mock new entity
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person)); // mockito actions when instance use findById(id);

        personService.delete(1L); // this var is for assertions check the result of operation

        verify(personRepository, times(1)).findById(anyLong()); // verify wants times this method called
        verify(personRepository, times(1)).delete(any(Person.class)); // verify wants times this method called
        verifyNoMoreInteractions(personRepository); // verify if repository don't have operations after method has been called.
    }

    @Test
    void findById() throws BadRequestException {
        Person person = input.mockEntity(1); // mock new entity
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person)); // mockito actions when instance use findById(id);

        var result = personService.findById(1L); // this var is for assertions check the result of operation

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks()); // get for check if Hateoas is not null

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1/find-all")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("save") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("POST"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("PUT"));

        result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("DELETE"));

        assertEquals("Address Test1", result.getAdress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(personRepository.findAll()).thenReturn(list);
        List<PersonDto> people = personService.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1/find-all")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("save") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("POST"));

        personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("PUT"));

        personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1")
                        && Objects.requireNonNull(link.getType()).equals("DELETE"));

        assertEquals("Address Test1", personOne.getAdress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFor = people.get(4);

        personFor.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/4")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        personFor.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1/find-all")
                        && Objects.requireNonNull(link.getType()).equals("GET"));

        personFor.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("save") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("POST"));

        personFor.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1")
                        && Objects.requireNonNull(link.getType()).equals("PUT"));

        personFor.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/4")
                        && Objects.requireNonNull(link.getType()).equals("DELETE"));

        assertEquals("Address Test4", personFor.getAdress());
        assertEquals("First Name Test4", personFor.getFirstName());
        assertEquals("Last Name Test4", personFor.getLastName());
        assertEquals("Male", personFor.getGender());
    }
}