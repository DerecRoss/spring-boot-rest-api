package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.dto.PersonDto;
import com.rest_api.spring_boot_rest_api.model.Person;
import com.rest_api.spring_boot_rest_api.repository.PersonRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseListObjects;
import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseObject;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository repository;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonDto save(PersonDto personDto){
        var entity = parseObject(personDto, Person.class);
        return parseObject(repository.save(entity), PersonDto.class);
    }

    public PersonDto update(PersonDto PersonDto) throws BadRequestException {
        Person entity = repository.findById(PersonDto.getId())
                .orElseThrow(() -> new BadRequestException("User not found."));
        entity.setFirstName(PersonDto.getFirstName());
        entity.setLastName(PersonDto.getLastName());
        entity.setAdress(PersonDto.getAdress());
        entity.setGender(PersonDto.getGender());

        return parseObject(repository.save(entity), PersonDto.class);
    }

    public void delete(Long id) throws BadRequestException {
        Person entity = repository.findById(id)
                        .orElseThrow(() -> new BadRequestException("User not found"));
        repository.delete(entity);
    }

    public PersonDto findById(Long id) throws BadRequestException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found."));

        return parseObject(entity, PersonDto.class);
    }

    public List<PersonDto> findAll(){
        return parseListObjects(repository.findAll(), PersonDto.class);
    }

    private PersonDto mockPersonDto(int i) {
        logger.info("Finding by PersonDto.");
        PersonDto PersonDto = new PersonDto();
        PersonDto.setId(counter.incrementAndGet());
        PersonDto.setFirstName("First Name " + i);
        PersonDto.setLastName("Last Name " + i);
        PersonDto.setAdress("SP" + i);
        PersonDto.setGender("M" + i);
        return PersonDto;
    }
}
