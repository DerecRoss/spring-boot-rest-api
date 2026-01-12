package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository repository;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person save(Person person){
        return repository.save(person);
    }

    public Person update(Person person) throws BadRequestException {
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new BadRequestException("User not found."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAdress(person.getAdress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) throws BadRequestException {
        repository.delete(findById(id));
    }

    public Person findById(Long id) throws BadRequestException {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found."));
    }

    public List<Person> findAll(){
        return repository.findAll();
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
