package com.raheemspence.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CreateCourseResponse {

    private String courseName;
    private String school;
    private Instant createdAt;
    private String creatorFirstName;
    private String creatorLastName;
    private Long creator_id;
    private String joinCode;
}
