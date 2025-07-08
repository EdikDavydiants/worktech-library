package org.task.worktech_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.task.worktech_library.model.entity.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findByName(String name);
}
