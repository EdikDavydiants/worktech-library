package org.task.worktech_library.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.task.worktech_library.controller.EditController;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.service.EditService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EditControllerImpl implements EditController {

    private final EditService editService;

    @Override
    public ResponseEntity<BookDto> addBook(BookDto bookDto) {
        editService.addBook(bookDto);
        return ResponseEntity.ok().body(bookDto);
    }

    @Override
    public ResponseEntity<Void> deleteBook(UUID id) {

        return null;
    }

    @Override
    public ResponseEntity<BookDto> updateBook(UUID id, BookDto bookDto) {

        return null;
    }
}
