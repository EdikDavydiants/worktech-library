package org.task.worktech_library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.task.worktech_library.exception.NotFoundException;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;
import org.task.worktech_library.repository.AuthorRepository;
import org.task.worktech_library.repository.BookRepository;
import org.task.worktech_library.repository.GenreRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private EditService editService;

    @Test
    void addBook_ShouldIncreaseQuantity_WhenBookExists() {
        BookDto bookDto = new BookDto("Existing Book", 100, List.of("Author"), List.of("Genre"));
        Book existingBook = Book.builder()
                .title("Existing Book")
                .authors(Set.of(Author.builder().name("Author").build()))
                .genres(Set.of(Genre.builder().name("Genre").build()))
                .quantity(1)
                .build();

        when(bookRepository.findByTitle("Existing Book")).thenReturn(List.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        editService.addBook(bookDto);

        assertEquals(2, existingBook.getQuantity());
        verify(bookRepository).save(existingBook);
    }

    @Test
    void addBook_ShouldCreateNewBook_WhenBookDoesNotExist() {
        BookDto bookDto = new BookDto("New Book", 100, List.of("Author"), List.of("Genre"));
        Author author = new Author(UUID.randomUUID(), "Author", new HashSet<>());
        Genre genre = new Genre(1, "Genre", new HashSet<>());

        when(bookRepository.findByTitle("New Book")).thenReturn(Collections.emptyList());
        when(authorRepository.findByName("Author")).thenReturn(Optional.of(author));
        when(genreRepository.findByName("Genre")).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        editService.addBook(bookDto);

        verify(bookRepository).save(any(Book.class));
        verify(authorRepository).findByName("Author");
        verify(genreRepository).findByName("Genre");
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenQuantityIsOne() {
        UUID bookId = UUID.randomUUID();
        Book book = Book.builder()
                .id(bookId)
                .quantity(1)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        editService.deleteBook(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void deleteBook_ShouldDecreaseQuantity_WhenQuantityMoreThanOne() {
        UUID bookId = UUID.randomUUID();
        Book book = Book.builder()
                .id(bookId)
                .quantity(2)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        editService.deleteBook(bookId);

        assertEquals(1, book.getQuantity());
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook_ShouldThrowException_WhenBookNotFound() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> editService.deleteBook(bookId));
    }

    @Test
    void updateBook_ShouldUpdateBookData() {
        UUID bookId = UUID.randomUUID();
        BookDto bookDto = new BookDto("Updated Title", 200, List.of("New Author"), List.of("New Genre"));

        Author oldAuthor = new Author(UUID.randomUUID(), "Old Author", new HashSet<>());
        Genre oldGenre = new Genre(1, "Old Genre", new HashSet<>());
        Book book = Book.builder()
                .id(bookId)
                .title("Old Title")
                .pageCount(100)
                .authors(new HashSet<>(Set.of(oldAuthor)))
                .genres(new HashSet<>(Set.of(oldGenre)))
                .build();

        Author newAuthor = new Author(UUID.randomUUID(), "New Author", new HashSet<>());
        Genre newGenre = new Genre(2, "New Genre", new HashSet<>());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findByName("New Author")).thenReturn(Optional.of(newAuthor));
        when(genreRepository.findByName("New Genre")).thenReturn(Optional.of(newGenre));
        when(bookRepository.save(book)).thenReturn(book);

        editService.updateBook(bookId, bookDto);

        assertEquals("Updated Title", book.getTitle());
        assertEquals(200, book.getPageCount());
        assertEquals(1, book.getAuthors().size());
        assertEquals(1, book.getGenres().size());
        assertTrue(book.getAuthors().contains(newAuthor));
        assertTrue(book.getGenres().contains(newGenre));
        verify(bookRepository).save(book);
    }
}
