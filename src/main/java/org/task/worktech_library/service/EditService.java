package org.task.worktech_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.task.worktech_library.exception.NotFoundException;
import org.task.worktech_library.mapper.BookMapper;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;
import org.task.worktech_library.repository.AuthorRepository;
import org.task.worktech_library.repository.BookRepository;
import org.task.worktech_library.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.task.worktech_library.util.StringMessages.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EditService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public void addBook(BookDto bookDto) {
        Optional<Book> existingBook = findEquivalentBook(bookDto);

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        } else {
            Book newBook = BookMapper.mapBookDtoToBook(bookDto);

            Set<Author> authors = processAuthors(bookDto.authors(), newBook);
            Set<Genre> genres = processGenres(bookDto.genres(), newBook);

            newBook.setAuthors(authors);
            newBook.setGenres(genres);
            newBook.setQuantity(1);

            bookRepository.save(newBook);
        }
    }

    private Optional<Book> findEquivalentBook(BookDto bookDto) {
        List<Book> booksWithSameTitle = bookRepository.findByTitle(bookDto.title());

        return booksWithSameTitle.stream()
                .filter(book -> {
                    Set<String> bookAuthors = book.getAuthors().stream()
                            .map(Author::getName)
                            .collect(Collectors.toSet());
                    boolean authorsMatch = new HashSet<>(bookDto.authors()).equals(bookAuthors);

                    Set<String> bookGenres = book.getGenres().stream()
                            .map(Genre::getName)
                            .collect(Collectors.toSet());
                    boolean genresMatch = new HashSet<>(bookDto.genres()).equals(bookGenres);

                    return authorsMatch && genresMatch;
                })
                .findFirst();
    }

    public Set<Author> processAuthors(List<String> authorNames, Book book) {
        return authorNames.stream()
                .map(authorName -> {
                    Author author = authorRepository.findByName(authorName)
                            .orElseGet(() -> {
                                Author newAuthor = new Author();
                                newAuthor.setName(authorName);
                                newAuthor.setBooks(new HashSet<>());
                                return authorRepository.saveAndFlush(newAuthor);
                            });
                    author.getBooks().add(book);
                    return author;
                })
                .collect(Collectors.toSet());
    }

    public Set<Genre> processGenres(List<String> genreNames, Book book) {
        return genreNames.stream()
                .map(genreName -> {
                    Genre genre = genreRepository.findByName(genreName)
                            .orElseGet(() -> {
                                Genre newGenre = new Genre();
                                newGenre.setName(genreName);
                                newGenre.setBooks(new HashSet<>());
                                return genreRepository.saveAndFlush(newGenre);
                            });
                    genre.getBooks().add(book);
                    return genre;
                })
                .collect(Collectors.toSet());
    }

    public void deleteBook(UUID id) {
        if(!bookRepository.existsById(id)) {
            throw new NotFoundException(BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateBook(UUID id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND));

        book.setTitle(bookDto.title());
        book.setPageCount(bookDto.pageCount());

        book.getAuthors().forEach(author -> author.getBooks().remove(book));
        book.getAuthors().clear();
        book.getGenres().forEach(genre -> genre.getBooks().remove(book));
        book.getGenres().clear();

        Set<Author> updatedAuthors = processAuthors(bookDto.authors(), book);
        book.setAuthors(updatedAuthors);
        Set<Genre> updatedGenres = processGenres(bookDto.genres(), book);
        book.setGenres(updatedGenres);

        bookRepository.save(book);
    }
}
