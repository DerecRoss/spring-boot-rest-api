package com.rest_api.spring_boot_rest_api.wrapper.xml;

import com.rest_api.spring_boot_rest_api.integrationtests.dto.PersonDto;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PagedModelPerson implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content") // == @JsonProperty
    public List<PersonDto> content; // Object <content> of XML

    public PagedModelPerson() {} // no args constructor for all frameworks in java

    public List<PersonDto> getContent() {
        return content;
    }

    public void setContent(List<PersonDto> content) {
        this.content = content;
    }
}
