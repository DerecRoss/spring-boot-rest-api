package com.rest_api.spring_boot_rest_api.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.PersonDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("people")
    private List<PersonDto> personDto;

    public PersonEmbeddedDto() {
    }

    public List<PersonDto> getPersonDto() {
        return personDto;
    }

    public void setPersonDto(List<PersonDto> personDto) {
        this.personDto = personDto;
    }
}
