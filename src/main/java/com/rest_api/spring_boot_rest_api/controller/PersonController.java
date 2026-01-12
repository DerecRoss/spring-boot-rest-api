package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.service.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) throws BadRequestException {
        Person person = personService.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Person>> findAll(){
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        Person entity = personService.save(person);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Person> put(@RequestBody Person person) throws BadRequestException {
        Person entity = personService.update(person);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BadRequestException {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
