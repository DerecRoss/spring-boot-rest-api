package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.dto.v1.BookDto;
import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.model.Book;
import com.rest_api.spring_boot_rest_api.repository.BookRepository;
import com.rest_api.spring_boot_rest_api.unittests.mapper.mocks.MockBoock;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBoock input;

    @InjectMocks
    private BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        input = new MockBoock();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Book book = input.mockEntity(1);
        Book bookInDb = book;

        bookInDb.setId(1L);

        BookDto bookDto = input.mockDTO(1);

        when(bookRepository.save(book)).thenReturn(bookInDb);

        var result = bookService.save(bookDto);

        assertNotNull(result);
        assertEquals(bookInDb.getId(), result.getId());
        assertNotNull(result.getId());

    }

    @Test
    void update() throws BadRequestException {
        Book book = input.mockEntity(1);
        Book bookInDb = book;

        bookInDb.setId(1L);

        BookDto bookDto = input.mockDTO(1);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(bookInDb);

        var result = bookService.update(bookDto);

        assertNotNull(result);
        assertEquals(bookInDb.getId(), result.getId());
        assertNotNull(result.getId());
    }

    @Test
    void delete() throws BadRequestException {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).delete(any(Book.class));

        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void findById() throws BadRequestException {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        var result = bookService.findById(book.getId());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
    }

    @Test
    void findAll() {
        List<Book> books = input.mockEntityList();
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> booksDto = bookService.findAll();

        assertNotNull(booksDto);
        assertEquals(14, booksDto.size());

        var bookOne = booksDto.get(1);
    }
}