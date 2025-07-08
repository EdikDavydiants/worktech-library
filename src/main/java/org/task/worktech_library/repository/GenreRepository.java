package org.task.worktech_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.task.worktech_library.model.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Optional<Genre> findByName(String name);
}
