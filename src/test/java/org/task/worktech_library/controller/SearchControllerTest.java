package org.task.worktech_library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.task.worktech_library.controller.impl.SearchControllerImpl;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.service.SearchService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchControllerImpl searchController;

    @Test
    void searchBooks_ShouldReturnPageOfBooks() {
        String title = "Test Book";
        String author = "Test Author";
        String genre = "Fantasy";
        Pageable pageable = PageRequest.of(0, 10);

        var book1 = new BookDto(
                "Test Book 1",
                555,
                List.of("Author1", "Author2"),
                List.of("Genre1", "Genre2"));
        var book2 = new BookDto(
                "Test Book 2",
                444,
                List.of("Author3"),
                List.of("Genre3"));
        Page<BookDto> mockPage = new PageImpl<>(List.of(book1, book2), pageable, 2);

        when(searchService.searchBooks(anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(mockPage);

        ResponseEntity<Page<BookDto>> response =
                searchController.searchBooks(title, author, genre, pageable);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().getTotalElements());
        assertEquals("Test Book 1", response.getBody().getContent().get(0).title());
    }

    @Test
    void searchBooks_WithNullParams_ShouldStillWork() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDto> mockPage = new PageImpl<>(List.of(), pageable, 0);

        when(searchService.searchBooks(null, null, null, pageable))
                .thenReturn(mockPage);

        ResponseEntity<Page<BookDto>> response =
                searchController.searchBooks(null, null, null, pageable);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().getTotalElements());
    }
}
