package com.raheemspence.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

import java.time.Instant;

/* This tells Spring/JPA that this class represents a database table. Each object becomes a row in the table
    @Table --> maps this entity to the users table, needed because class name is User but table name is users so
    this explicitly connects them
 */
@Entity
@Table(name = "users")
/*
    Using Lombok here --  it is a library that automatically generates getters, setters, constructors, equals, hashCode,
    etc. at compile time. It reduces writing boilerplate code.
 */

// These two generate getter and setter methods for all fields
@Getter
@Setter

// REQUIRED by JPA/Hibernate, generates empty constructor to create objects from db rows and instantiate entities via
// reflection...w/o it the app breaks at runtime
@NoArgsConstructor
public class User {

    // Marks this field as the primary key of the table
    @Id

    // Means let the db auto-generate this ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Private fields are a core part of encapsulation = hiding internal data and only allowing controlled access to it
    /*
        We use private on class fields to enforce encapsulation, meaning the internal state of an object is hidden from
        outside classes and can only be accessed or modified through controlled methods. This prevents direct, uncontrolled
        changes to data, helps mainting data integrity, and allows validation or logic to be added when values are read
        or updated.

        Data integrity = make sure data stays:
        - correct (no fake/invalid values)
        - consistent (same rules everywhere)
        - reliable (doesn't randomly break or change incorrectly)
     */
    private Long id;

    /*
        @Column tells JPA how a field maps to a db column and what rules that column must follow.
        If not specified, JPA just assumes defaults

        Database constraints enforce correctness at runtime, while @Column mirrors those rules in your Java model to
        improve clarity, early validation, and maintainability

        Its useful because:
        1. Early error detection (before hitting db)
        e.g. @Column(nullable = false) -- Spring can catch bad data BEFORE it even tries to insert it into Postgres

        2. Prevent accidental bad queries in code
        W/o @Column(nullable = false):
        - Java code might send null
        - db throws error later (runtime)
        w/
        - validation can happen earlier in the stack

        3. Keeps Java model aligned with db
        The Java entity becomes a "mirror" of the schema
        So when the code is read you immediately know:
        - whats required
        - whats unique
        - whats optional

        4. Helps if schema changes later
        Imagine:
        - you move db
        - or regenerate schema
        - or use ddl-auto=update
        then JPA becomes the source of truth
     */
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // Instant = timestamp in UTC (Java time type)
    // Instant.now() = current time when object is created
    // IMPORTANT! Java sets the value when the object is created not the db
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

}
