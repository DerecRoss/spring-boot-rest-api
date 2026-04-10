package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.controller.docs.PersonControllerDocs;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.dto.v2.PersonDtoV2;
import com.rest_api.spring_boot_rest_api.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person/v1")
//@CrossOrigin(origins = "http://localhost:8080")
@Tag(name = "People", description = "Endpoints for manage peoples.")
public class PersonController implements PersonControllerDocs {

    @Autowired
    public PersonService personService;

//    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) throws BadRequestException {
        PersonDto person = personService.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName"));

        return new ResponseEntity<>(personService.findAll(pageable), HttpStatus.OK);
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://github.com/DerecRoss"})
    @PostMapping
    @Override
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto person) {
        PersonDto entity = personService.save(person);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PostMapping("/v2")
    public ResponseEntity<PersonDtoV2> saveV2(@RequestBody PersonDtoV2 person) {
        PersonDtoV2 entity = personService.saveV2(person);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    @Override
    public ResponseEntity<PersonDto> put(@RequestBody PersonDto person) throws BadRequestException {
        PersonDto entity = personService.update(person);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<PersonDto> disablePerson(@PathVariable Long id) throws BadRequestException {
        return new ResponseEntity<>(personService.disablePerson(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BadRequestException {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
