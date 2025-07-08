package org.task.worktech_library.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;
import org.task.worktech_library.repository.BookRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private SearchService searchService;

    private static Book book;

    @BeforeAll
    public static void setUp() {
        book = Book.builder()
                .title("Test Book")
                .authors(Set.of(Author.builder().name("Author").build()))
                .genres(Set.of(Genre.builder().name("Fantasy").build()))
                .pageCount(111)
                .build();
    }

    @Test
    void searchBooks_WithTitleFilter_ShouldReturnFilteredBooks() {
        String title = "Test Book";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(title, null, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Book", result.getContent().get(0).title());
        verify(bookRepository).findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable));
    }

    @Test
    void searchBooks_WithAuthorFilter_ShouldReturnFilteredBooks() {
        String author = "Author";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(null, author, null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Author", result.getContent().get(0).authors().get(0));
    }

    @Test
    void searchBooks_WithGenreFilter_ShouldReturnFilteredBooks() {
        String genre = "Fantasy";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(null, null, genre, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Fantasy", result.getContent().get(0).genres().get(0));
    }

    @Test
    void searchBooks_WithAllFilters_ShouldReturnFilteredBooks() {
        String title = "Test Book";
        String author = "Author";
        String genre = "Fantasy";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(title, author, genre, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Book", result.getContent().get(0).title());
        assertEquals("Author", result.getContent().get(0).authors().get(0));
        assertEquals("Fantasy", result.getContent().get(0).genres().get(0));
    }

    @Test
    void searchBooks_WithEmptyFilters_ShouldReturnAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        var book1 = Book.builder()
                .title("Book 1")
                .authors(Set.of(Author.builder().name("Author 1").build()))
                .genres(Set.of(Genre.builder().name("Fantasy").build()))
                .pageCount(111)
                .build();
        var book2 = Book.builder()
                .title("Book 2")
                .authors(Set.of(Author.builder().name("Author 2").build()))
                .genres(Set.of(Genre.builder().name("Sci-Fi").build()))
                .pageCount(222)
                .build();
        Page<Book> mockPage = new PageImpl<>(List.of(book1, book2), pageable, 2);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(null, null, null, pageable);

        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().stream().anyMatch(b -> b.title().equals("Book 1")));
        assertTrue(result.getContent().stream().anyMatch(b -> b.title().equals("Book 2")));
    }

    @Test
    void searchBooks_WithBlankFilters_ShouldIgnoreThem() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable)))
                .thenReturn(mockPage);

        Page<BookDto> result = searchService.searchBooks(" ", " ", " ", pageable);

        assertEquals(1, result.getTotalElements());
        verify(bookRepository).findAll(ArgumentMatchers.<Specification<Book>>any(), eq(pageable));
    }
}
