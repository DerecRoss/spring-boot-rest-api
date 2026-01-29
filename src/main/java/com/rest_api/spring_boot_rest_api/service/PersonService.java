package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.controller.PersonController;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.dto.v2.PersonDtoV2;
import com.rest_api.spring_boot_rest_api.exception.RequiredObjectIsNonNullException;
import com.rest_api.spring_boot_rest_api.mapper.custom.PersonMapper;
import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.repository.PersonRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseListObjects;
import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseObject;
import static com.rest_api.spring_boot_rest_api.mapper.custom.PersonMapper.convertDtoV2ToEntity;
import static com.rest_api.spring_boot_rest_api.mapper.custom.PersonMapper.convertEntityToDtoV2;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper personMapper;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonDto save(PersonDto personDto){

        if (personDto == null) throw new RequiredObjectIsNonNullException();

        var entity = parseObject(personDto, Person.class);

        logger.info("Saving Person in database.");
        var dto = parseObject(repository.save(entity), PersonDto.class);
        try {
            addHateOsLinks(dto);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    public PersonDtoV2 saveV2(PersonDtoV2 personDtoV2){
        var entity = convertDtoV2ToEntity(personDtoV2);

        logger.info("Saving Person in database. - V2");
        return convertEntityToDtoV2(repository.save(entity));
    }

    public PersonDto update(PersonDto personDto) throws BadRequestException {

        if (personDto == null) throw new RequiredObjectIsNonNullException();

        Person entity = repository.findById(personDto.getId())
                .orElseThrow(() -> new BadRequestException("User not found."));
        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAdress(personDto.getAdress());
        entity.setGender(personDto.getGender());

        logger.info("Update Person in database.");
        var dto = parseObject(repository.save(entity), PersonDto.class);
        addHateOsLinks(dto);
        return dto;
    }

    public void delete(Long id) throws BadRequestException {
        Person entity = repository.findById(id)
                        .orElseThrow(() -> new BadRequestException("User not found"));

        logger.info("Delete Person in database.");
        repository.delete(entity);
    }

    public PersonDto findById(Long id) throws BadRequestException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found."));

        logger.info("Finding Person in database.");
        var dto = parseObject(entity, PersonDto.class);
        addHateOsLinks(dto); // manual method
        return dto;
    }

    public List<PersonDto> findAll(){
        logger.info("Finding All Person in database.");
        var persons = parseListObjects(repository.findAll(), PersonDto.class);
        persons.forEach(p -> {
            try {
                addHateOsLinks(p);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });
        return persons;
    }

    private void addHateOsLinks(PersonDto dto) throws BadRequestException {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("find-all").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(PersonController.class).save(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).put(dto)).withRel("update").withType("PUT"));
    }
}
