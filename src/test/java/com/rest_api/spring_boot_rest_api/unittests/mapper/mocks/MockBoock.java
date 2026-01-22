package com.rest_api.spring_boot_rest_api.unittests.mapper.mocks;

import com.rest_api.spring_boot_rest_api.dto.v1.BookDto;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.model.Book;
import com.rest_api.spring_boot_rest_api.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBoock {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDto mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> persons = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<BookDto> mockDTOList() {
        List<BookDto> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book books = new Book();
        books.setAuthor("Author Test" + number);
        books.setPrice(25F);
        books.setId(number.longValue());
        books.setDate(new Date());
        books.setTitle("Title Test" + number);
        return books;
    }

    public BookDto mockDTO(Integer number) {


        BookDto book = new BookDto();
        book.setAuthor("Author Test" + number);
        book.setPrice(25F);
        book.setId(1L);
        book.setDate(new Date());
        book.setTitle("Title Test" + number);
        return book;
    }

}