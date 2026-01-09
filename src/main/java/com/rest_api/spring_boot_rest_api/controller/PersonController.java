package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id){
        return personService.findById(id);
    }

    @GetMapping("/find-all")
    public List<Person> findAll(){
        return personService.findAll();
    }

    @PostMapping
    public Person save(@RequestBody Person person){
        return personService.save(person);
    }

    @PutMapping
    public Person put(@RequestBody Person person){
        return personService.save(person);
    }


}
