package com.raheemspence.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NoteResponse {

    private Long id;
    private String title;
    private String content;
    private Instant createdAt;
    private Long ownerId;
    private String ownerFirstName;
    private String ownerLastName;
    private String courseName;
    private Long courseId;
}
