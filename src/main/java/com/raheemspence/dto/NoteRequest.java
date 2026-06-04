package com.raheemspence.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NoteRequest {

    /*
        This annotation tells Spring -- before this request reaches my controller method, validate that this field is
        not null, not empty, and not just spaces
     */
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;
}
