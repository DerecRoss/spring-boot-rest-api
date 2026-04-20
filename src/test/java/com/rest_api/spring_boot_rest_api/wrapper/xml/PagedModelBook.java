package com.rest_api.spring_boot_rest_api.wrapper.xml;

import com.rest_api.spring_boot_rest_api.integrationtests.dto.BookDto;
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
public class PagedModelBook implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content") // == @JsonProperty
    public List<BookDto> content; // Object <content> of XML

    public PagedModelBook() {
    }

    public List<BookDto> getContent() {
        return content;
    }

    public void setContent(List<BookDto> content) {
        this.content = content;
    }
}
