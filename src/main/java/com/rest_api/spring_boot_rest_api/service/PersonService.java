package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person save(Person person){
        logger.info("creating person.");
        return person;
    }

    public Person put(Person person){
        logger.info("updating person.");
        return person;
    }

    public Void put(Long id){
        logger.info("deleting person.");
        return null;
    }

    public Person findById(Long id){
        logger.info("Finding by Person.");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Bart");
        person.setLastName("Simpson");
        person.setAdress("SP");
        person.setGender("M");
        return person;
    }

    public List<Person> findAll(){
        ArrayList<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person  = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    private Person mockPerson(int i) {
        logger.info("Finding by Person.");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("First Name " + i);
        person.setLastName("Last Name " + i);
        person.setAdress("SP" + i);
        person.setGender("M" + i);
        return person;
    }
}
