package org.task.worktech_library.model.dto;

import java.util.List;

public record BookDto(

        String title,
        int pageCount,
        List<String> authors,
        List<String> genres
) {
}
