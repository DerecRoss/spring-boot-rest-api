package com.rest_api.spring_boot_rest_api.service;

import com.rest_api.spring_boot_rest_api.controller.BookController;
import com.rest_api.spring_boot_rest_api.dto.v1.BookDto;
import com.rest_api.spring_boot_rest_api.exception.RequiredObjectIsNonNullException;
import com.rest_api.spring_boot_rest_api.model.Book;
import com.rest_api.spring_boot_rest_api.repository.BookRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseListObjects;
import static com.rest_api.spring_boot_rest_api.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookDto> assembler;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public BookDto save(BookDto bookDto){

        if (bookDto == null) throw new RequiredObjectIsNonNullException();

        var entity = parseObject(bookDto, Book.class);

        logger.info("Saving Person in database.");
        var dto = parseObject(repository.save(entity), BookDto.class);
        try {
            addHateOsLinks(dto);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    public BookDto update(BookDto bookDto) throws BadRequestException {

        if (bookDto == null) throw new RequiredObjectIsNonNullException();

        Book entity = repository.findById(bookDto.getId())
                .orElseThrow(() -> new BadRequestException("User not found."));

        entity.setAuthor(bookDto.getAuthor());
        entity.setDate(bookDto.getDate());
        entity.setPrice(bookDto.getPrice());
        entity.setTitle(bookDto.getTitle());

        logger.info("Update Person in database.");
        var dto = parseObject(repository.save(entity), BookDto.class);
        addHateOsLinks(dto);
        return dto;
    }

    public void delete(Long id) throws BadRequestException {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));

        logger.info("Delete Person in database.");
        repository.delete(entity);
    }

    public BookDto findById(Long id) throws BadRequestException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found."));

        logger.info("Finding Person in database.");
        var dto = parseObject(entity, BookDto.class);
        addHateOsLinks(dto); // manual method
        return dto;
    }

    public PagedModel<EntityModel<BookDto>> findAll(Pageable pageable){
        logger.info("Finding All Person in database.");

        var books = repository.findAll(pageable);

        var booksWithLinks = books.map(b -> {
            var dto = parseObject(b, BookDto.class);
            try {
                addHateOsLinks(dto);
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
            return dto;
        });

        Link findAllLinks = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    String.valueOf(pageable.getSort())
        )).withSelfRel();

        return assembler.toModel(booksWithLinks, findAllLinks);
    }

    private void addHateOsLinks(BookDto dto) throws BadRequestException {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));

        dto.add(linkTo(methodOn(BookController.class).findAll(0, 12, "asc")).withRel("find-all").withType("GET"));

        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        dto.add(linkTo(methodOn(BookController.class).save(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(BookController.class).put(dto)).withRel("update").withType("PUT"));
    }
}
