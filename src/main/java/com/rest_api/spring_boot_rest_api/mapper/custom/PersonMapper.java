package com.rest_api.spring_boot_rest_api.mapper.custom;

import com.rest_api.spring_boot_rest_api.dto.v2.PersonDtoV2;
import com.rest_api.spring_boot_rest_api.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

        public static PersonDtoV2 convertEntityToDtoV2(Person person){
            PersonDtoV2 dto = new PersonDtoV2();
            dto.setId(person.getId());
            dto.setFirstName(person.getFirstName());
            dto.setLastName(person.getLastName());
            dto.setAdress(person.getAdress());
            dto.setBirthday(new Date());
            dto.setGender(person.getGender());
            return dto;
        }

    public static Person convertDtoV2ToEntity(PersonDtoV2 personDtoV2){
        Person dto = new Person();

        dto.setFirstName(personDtoV2.getFirstName());
        dto.setLastName(personDtoV2.getLastName());
        dto.setAdress(personDtoV2.getAdress());
//        dto.setBirthday(new Date());
        dto.setGender(personDtoV2.getGender());
        return dto;
    }

}
