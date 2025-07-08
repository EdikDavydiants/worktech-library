package org.task.worktech_library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.task.worktech_library.model.dto.BookDto;

import java.util.UUID;

@RequestMapping("/book/edit")
public interface EditController {

    @Operation(summary = "Add a new book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book has been added."),
            @ApiResponse(responseCode = "500", description = "Internal server error!")
    })
    @PostMapping("/add")
    ResponseEntity<Void> addBook(@RequestBody BookDto bookDto);

    @Operation(summary = "Delete book by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book has been deleted."),
            @ApiResponse(responseCode = "404", description = "Book has not been found!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")
    })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable UUID id);

    @Operation(summary = "Update book data by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book has been updated."),
            @ApiResponse(responseCode = "404", description = "Book has not been found!"),
            @ApiResponse(responseCode = "500", description = "Internal server error!")
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<Void> updateBook(@PathVariable UUID id, @RequestBody BookDto bookDto);
}
