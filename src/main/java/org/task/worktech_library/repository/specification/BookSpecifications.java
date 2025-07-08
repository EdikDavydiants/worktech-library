package org.task.worktech_library.repository.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.task.worktech_library.model.entity.Author;
import org.task.worktech_library.model.entity.Book;
import org.task.worktech_library.model.entity.Genre;

public class BookSpecifications {
    public static Specification<Book> hasTitleContaining(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorNameContaining(String authorName) {
        return (root, query, cb) -> {
            Join<Book, Author> authors = root.join("authors");
            return cb.like(cb.lower(authors.get("name")), "%" + authorName.toLowerCase() + "%");
        };
    }

    public static Specification<Book> hasGenreNameContaining(String genreName) {
        return (root, query, cb) -> {
            Join<Book, Genre> genres = root.join("genres");
            return cb.like(cb.lower(genres.get("name")), "%" + genreName.toLowerCase() + "%");
        };
    }
}