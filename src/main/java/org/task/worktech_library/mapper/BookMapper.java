package org.task.worktech_library.mapper;

import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;

import java.util.HashSet;
import java.util.UUID;

public class BookMapper {

    public static Book mapBookDtoToBook(BookDto bookDto) {
        return Book.builder()
                .title(bookDto.title())
                .pageCount(bookDto.pageCount())
                .authors(new HashSet<>())
                .genres(new HashSet<>())
                .build();
    }
}
