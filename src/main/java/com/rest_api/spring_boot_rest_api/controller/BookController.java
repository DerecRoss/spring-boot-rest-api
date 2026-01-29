package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.controller.docs.BookControllerDocs;
import com.rest_api.spring_boot_rest_api.dto.v1.BookDto;
import com.rest_api.spring_boot_rest_api.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for manage books.")
public class BookController implements BookControllerDocs {
    @Autowired
    public BookService bookService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<BookDto> findById(@PathVariable Long id) throws BadRequestException {
        BookDto book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    @Override
    public ResponseEntity<List<BookDto>> findAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<BookDto> save(@RequestBody BookDto book) {
        BookDto entity = bookService.save(book);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    @Override
    public ResponseEntity<BookDto> put(@RequestBody BookDto book) throws BadRequestException {
        BookDto entity = bookService.update(book);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BadRequestException {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
