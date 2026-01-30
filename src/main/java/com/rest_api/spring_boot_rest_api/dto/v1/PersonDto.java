package com.rest_api.spring_boot_rest_api.dto.v1;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PersonDto extends RepresentationModel<PersonDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String adress;

    private String gender;

    private Boolean enabled;

    public PersonDto() {
    }

    public PersonDto(Long id, String firstName, String lastName, String adress, String gender, Boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.gender = gender;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDto personDto = (PersonDto) o;
        return Objects.equals(id, personDto.id) && Objects.equals(firstName, personDto.firstName) && Objects.equals(lastName, personDto.lastName) && Objects.equals(adress, personDto.adress) && Objects.equals(gender, personDto.gender) && Objects.equals(enabled, personDto.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, lastName, adress, gender, enabled);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
