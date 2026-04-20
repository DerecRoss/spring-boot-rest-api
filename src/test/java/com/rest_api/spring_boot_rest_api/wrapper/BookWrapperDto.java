package com.rest_api.spring_boot_rest_api.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class BookWrapperDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedDto bookEmbeddedDto;

    public BookWrapperDto() {
    }

    public BookWrapperDto(BookEmbeddedDto bookEmbeddedDto) {
        this.bookEmbeddedDto = bookEmbeddedDto;
    }

    public BookEmbeddedDto getBookEmbeddedDto() {
        return bookEmbeddedDto;
    }

    public void setBookEmbeddedDto(BookEmbeddedDto bookEmbeddedDto) {
        this.bookEmbeddedDto = bookEmbeddedDto;
    }
}
