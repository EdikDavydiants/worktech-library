package org.task.worktech_library.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BookDto(

        String title,
        int pageCount,
        List<String> authors,
        List<String> genres
) {
}
