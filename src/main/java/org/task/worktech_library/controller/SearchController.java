package org.task.worktech_library.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.task.worktech_library.model.dto.BookDto;

@RequestMapping("/book/search")
public interface SearchController {

    @GetMapping
    @Operation(summary = "Поиск по названию, автору и жанру.")
    ResponseEntity<Page<BookDto>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable);
}
