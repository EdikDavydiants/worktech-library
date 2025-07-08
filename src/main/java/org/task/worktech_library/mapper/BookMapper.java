package org.task.worktech_library.mapper;

import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book mapBookDtoToBook(BookDto bookDto) {
        return Book.builder()
                .title(bookDto.title())
                .pageCount(bookDto.pageCount())
                .authors(new HashSet<>())
                .genres(new HashSet<>())
                .build();
    }

    public static BookDto mapBookToBookDto(Book book) {
        List<String> authorNames = book.getAuthors().stream().map(Author::getName).toList();
        List<String> genreNames = book.getGenres().stream().map(Genre::getName).toList();

        return BookDto.builder()
                .title(book.getTitle())
                .pageCount(book.getPageCount())
                .authors(authorNames)
                .genres(genreNames)
                .build();
    }
}
