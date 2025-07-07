package org.task.worktech_library.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.task.worktech_library.controller.SearchController;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {

    private final SearchService searchService;

    @Override
    public ResponseEntity<Page<BookDto>> searchBooks(String title, String author,
                                                     String genre, Pageable pageable) {

        return ResponseEntity.ok(searchService.searchBooks(title, author, genre, pageable));
    }
}
