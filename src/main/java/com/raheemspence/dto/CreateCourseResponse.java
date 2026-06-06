package com.raheemspence.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CreateCourseResponse {

    private String className;
    private String school;
    private Instant createdAt;
    private String creatorUsername;
    private Long creator_id;
    private String joinCode;
}
