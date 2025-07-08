package org.task.worktech_library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.task.worktech_library.exception.AlreadyExistsException;
import org.task.worktech_library.model.dto.UserRegistrationDto;
import org.task.worktech_library.model.entity.User;
import org.task.worktech_library.repository.UserRepository;

import static org.task.worktech_library.util.StringMessages.USER_ALREADY_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegistrationDto registrationDto) {
        if (userRepository.existsById(registrationDto.getEmail())) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));

        userRepository.save(user);
        log.info("User {} registered", registrationDto.getUsername());
    }
}
