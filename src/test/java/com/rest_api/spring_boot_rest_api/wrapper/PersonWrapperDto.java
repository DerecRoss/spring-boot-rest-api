package com.rest_api.spring_boot_rest_api.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class PersonWrapperDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private PersonEmbeddedDto personEmbeddedDto;

    public PersonWrapperDto(PersonEmbeddedDto personEmbeddedDto) {
        this.personEmbeddedDto = personEmbeddedDto;
    }

    public PersonWrapperDto() {
    }

    public PersonEmbeddedDto getPersonEmbeddedDto() {
        return personEmbeddedDto;
    }

    public void setPersonEmbeddedDto(PersonEmbeddedDto personEmbeddedDto) {
        this.personEmbeddedDto = personEmbeddedDto;
    }
}
