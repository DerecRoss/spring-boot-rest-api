package com.rest_api.spring_boot_rest_api.controller;

import com.rest_api.spring_boot_rest_api.dto.v1.PersonDto;
import com.rest_api.spring_boot_rest_api.dto.v2.PersonDtoV2;
import com.rest_api.spring_boot_rest_api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for manage peoples.")
public class PersonController {

    @Autowired
    public PersonService personService;

    @Operation(summary = "Find by ID", description = "Find specific person by ID", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = PersonDto.class))),

            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) throws BadRequestException {
        PersonDto person = personService.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    @Operation(summary = "Find all people", description = "Find all people", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))
                    )
            }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<PersonDto>> findAll(){
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Save new Person", description = "Save new Person with Dto", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))),

            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto person){
        PersonDto entity = personService.save(person);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PostMapping("/v2")
    public ResponseEntity<PersonDtoV2> saveV2(@RequestBody PersonDtoV2 person){
        PersonDtoV2 entity = personService.saveV2(person);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update exists Person", description = "Update Person in Database", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))),

            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<PersonDto> put(@RequestBody PersonDto person) throws BadRequestException {
        PersonDto entity = personService.update(person);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @Operation(summary = "Delete person by ID", description = "Delete exists person by ID", tags = {"People"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))),

            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws BadRequestException {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
