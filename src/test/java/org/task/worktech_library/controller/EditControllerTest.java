package org.task.worktech_library.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.task.worktech_library.controller.impl.EditControllerImpl;
import org.task.worktech_library.model.dto.BookDto;
import org.task.worktech_library.service.EditService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EditControllerTest {

    @Mock
    private EditService editService;

    @InjectMocks
    private EditControllerImpl editController;

    private static BookDto bookDto;

    @BeforeAll
    static void setUp() {
        bookDto = new BookDto(
                "Test Book",
                555,
                List.of("Author1", "Author2"),
                List.of("Genre1", "Genre2"));
    }

    @Test
    void addBook_ShouldReturn201Created() {
        ResponseEntity<Void> response = editController.addBook(bookDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(editService).addBook(bookDto);
    }

    @Test
    void deleteBook_ShouldReturn204NoContent() {
        UUID id = UUID.randomUUID();
        ResponseEntity<Void> response = editController.deleteBook(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(editService).deleteBook(id);
    }

    @Test
    void updateBook_ShouldReturn204NoContent() {
        UUID id = UUID.randomUUID();
        ResponseEntity<Void> response = editController.updateBook(id, bookDto);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(editService).updateBook(id, bookDto);
    }
}
