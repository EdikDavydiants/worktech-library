package org.task.worktech_library.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.task.worktech_library.controller.EditController;
import org.task.worktech_library.model.dto.BookDto;

import java.util.UUID;

@RestController
public class EditControllerImpl implements EditController {

    @Override
    public String addBook(BookDto bookDto) {

        return null;
    }

    @Override
    public String deleteBook(UUID id) {

        return null;
    }

    @Override
    public String updateBook(UUID id, BookDto bookDto) {

        return null;
    }
}
