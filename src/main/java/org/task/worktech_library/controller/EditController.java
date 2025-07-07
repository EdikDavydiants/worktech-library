package org.task.worktech_library.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.worktech_library.model.dto.BookDto;

import java.util.UUID;

@RestController
@RequestMapping("/book/edit")
public class EditController {

    @PostMapping("/add")
    public String addBook(@RequestBody BookDto bookDto) {

        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable UUID id) {

        return null;
    }

    @PatchMapping("/update/{id}")
    public String updateBook(@PathVariable UUID id, @RequestBody BookDto bookDto) {

        return null;
    }
}
