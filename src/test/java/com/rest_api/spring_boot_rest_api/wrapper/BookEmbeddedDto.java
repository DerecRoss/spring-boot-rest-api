package com.rest_api.spring_boot_rest_api.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rest_api.spring_boot_rest_api.integrationtests.dto.BookDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BookEmbeddedDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("bookDtoList")
    private List<BookDto> bookDtoList;

    public BookEmbeddedDto() {
    }

    public List<BookDto> getBookDtoList() {
        return bookDtoList;
    }

    public void setBookDtoList(List<BookDto> bookDtoList) {
        this.bookDtoList = bookDtoList;
    }
}
