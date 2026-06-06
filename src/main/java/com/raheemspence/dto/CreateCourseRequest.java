package com.raheemspence.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourseRequest {

    @NotBlank(message = "Class name cannot be empty")
    private String className;

    @NotBlank(message = "School cannot be empty")
    private String school;

    @NotBlank(message = "Join code cannot be empty")
    private String joinCode;
}
