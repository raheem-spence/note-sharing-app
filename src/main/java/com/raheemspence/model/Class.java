package com.raheemspence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
public class Class {

    // Class id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Class name
    @Column(nullable = false)
    private String name;

    // Class description (optional)
    private String description;

    // School
    @Column(nullable = false)
    private String school;

    // Professor (optional)
    private String professor;

    // Semester (optional)
    private String semester;

    // Join code
    @Column(nullable = false, unique = true)
    private String joinCode;

    // Creation time
    @CreationTimestamp
    private Instant createdAt;

    // Who created it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")

    /*
     This line tells hibernate that this fields stores the relationship to another entity

     We need User creator so Hibernate can automatically convert a foreign key (creator_id) into a usable User object,
     letting us navigate relationships instead of manually querying IDs.
     */
    private User creator;

}
