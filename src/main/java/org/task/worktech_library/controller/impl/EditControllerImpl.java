package org.task.worktech_library.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.task.worktech_library.controller.EditController;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.service.EditService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EditControllerImpl implements EditController {

    private final EditService editService;

    @Override
    public ResponseEntity<Void> addBook(BookDto bookDto) {
        editService.addBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteBook(UUID id) {
        editService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateBook(UUID id, BookDto bookDto) {
        editService.updateBook(id, bookDto);
        return ResponseEntity.noContent().build();
    }
}
