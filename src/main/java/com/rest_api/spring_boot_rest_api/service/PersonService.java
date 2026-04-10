package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.controller.PersonController;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.dto.v2.PersonDtoV2;
import com.rest_api.spring_boot_rest_api.exception.RequiredObjectIsNonNullException;
import com.rest_api.spring_boot_rest_api.mapper.custom.PersonMapper;
import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

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

    @Autowired
    PagedResourcesAssembler<PersonDto> assembler;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonDto save(PersonDto personDto){

        if (personDto == null) throw new RequiredObjectIsNonNullException();

        var entity = parseObject(personDto, Person.class);

        logger.info("Saving Person in database.");

        entity.setEnabled(true);

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

    @Transactional // for ACID requirements, this operation is not managed by JPA.
    public PersonDto disablePerson(Long id) throws BadRequestException {
        repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        logger.info("Disabling Person in database.");
        repository.disablePerson(id);
        var entity = repository.findById(id).get();

        var dto = parseObject(entity, PersonDto.class);
        addHateOsLinks(dto);
        return dto;
    }

    public PersonDto findById(Long id) throws BadRequestException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found."));

        logger.info("Finding Person in database.");
        var dto = parseObject(entity, PersonDto.class);
        addHateOsLinks(dto); // manual method
        return dto;
    }

    public PagedModel<EntityModel<PersonDto>> findAll(Pageable pageable){
        logger.info("Finding All Person in database.");


        var people = repository.findAll(pageable);

        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDto.class);
            try {
                addHateOsLinks(dto);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(PersonController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    private void addHateOsLinks(PersonDto dto) throws BadRequestException {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).findAll(0, 12, "asc")).withRel("find-all").withType("GET"));

        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(PersonController.class).save(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class).put(dto)).withRel("update").withType("PUT"));

        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
    }
}
