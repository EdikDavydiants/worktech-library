package org.task.worktech_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.task.worktech_library.mapper.BookMapper;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.repository.BookRepository;
import org.task.worktech_library.repository.specification.BookSpecifications;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BookRepository bookRepository;

    public Page<BookDto> searchBooks(String title, String author, String genre, Pageable pageable) {
        Specification<Book> spec = (root, query, cb) -> null;

        if (title != null && !title.isBlank()) {
            spec = spec.and(BookSpecifications.hasTitleContaining(title));
        }
        if (author != null && !author.isBlank()) {
            spec = spec.and(BookSpecifications.hasAuthorNameContaining(author));
        }
        if (genre != null && !genre.isBlank()) {
            spec = spec.and(BookSpecifications.hasGenreNameContaining(genre));
        }

        return bookRepository.findAll(spec, pageable)
                .map(BookMapper::mapBookToBookDto);
    }
}
