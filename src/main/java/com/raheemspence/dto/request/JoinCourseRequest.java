package com.raheemspence.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinCourseRequest {

    @NotBlank(message = "Course name cannot be empty")
    private String courseName;

    @NotBlank(message = "Join code cannot be empty")
    private String joinCode;



}
