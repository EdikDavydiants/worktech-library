package org.task.worktech_library.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.task.worktech_library.model.dto.BookDto;

import java.util.UUID;

@RequestMapping("/book/edit")
public interface EditController {

    @PostMapping("/add")
    @Operation(summary = "Добавить новую книгу.")
    String addBook(@RequestBody BookDto bookDto);

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить книгу по id.")
    String deleteBook(@PathVariable UUID id);

    @PatchMapping("/update/{id}")
    @Operation(summary = "Обновить данные книги по id.")
    String updateBook(@PathVariable UUID id, @RequestBody BookDto bookDto);
}
