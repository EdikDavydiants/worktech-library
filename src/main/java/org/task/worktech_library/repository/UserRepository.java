package org.task.worktech_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.task.worktech_library.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
}
