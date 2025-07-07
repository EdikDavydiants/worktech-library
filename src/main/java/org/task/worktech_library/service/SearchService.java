package org.task.worktech_library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.task.worktech_library.model.dto.BookDto;

@Service
public class SearchService {

    public Page<BookDto> searchBooks(String title, String author, String genre, Pageable pageable) {

        return null;
    }
}
