package com.raheemspence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

/* @Entity: this annotation is used to mark a Java class as a persistent data object, meaning it maps directly to a table in
    a relational database (e.g. postgreSQL)

    In other words, we are telling Java Persistence API (JPA) that this class represents a table in a db. And each
    instance (object) of the class represents a row of data in the table. The fields represent columns in the table.

    To work properly, it  must have three things:
    1. Primary Key: the class must have a field marked with @Id to act as the unique primary key for the db table
    2. No-Args Constructor: the class must include a public or protected constructor with no arguments so JPA can
    instantiate it.
    3. Not Final: the class cannot be declared as final.
 */
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String classId;
    private String text;

    /*  No-args constructor which is used by hibernate. The process goes like this:
        1. A GET request arrives at the Controller (e.g. to get all notes)
        2. Controller calls repository where hibernate lives (called the repository layer for spring boot
        aka data access layer more generally)
        3. Hibernate translate Java fields of the class to db columns either by using @Column annotation or converting
        camelCase -> snake_case (how columns are describe in the db)
        4. Then hibernate communicates with the db via SQL query to fetch the data it needs
        5. The db finds the row and sends the structured data grid
        6. Hibernate uses the no-args constructor to make a blank Java object (really null values and 0s for ints)
        7. Hibernate matches the column headers of the data it received to the fields and injects the data where it
        belongs and hands you object
        8. Spring boot turns that full Java object into JSON and sends it back to the browser.
     */

    // No-args constructor for hibernate (db fetches)
    public Note() {
    }


    // Args constructor for me (creating new note objects)
    public Note(Integer id, String classId, String text) {
        this.id = id;
        this.classId = classId;
        this.text = text;
    }

    /* Getters and setters
        Think of a private field as a locked safe inside a class. No other class in the application can interact with
        it without the getters and setters. This is encapsulation: hiding raw data (fields) inside a protective capsule
        and only exposing controlled ways to interact with it (the methods). The private keyword is the literal wall
        or shield that creates the capsule.

        NOTE: You only use getters and setters when you have the object already in Java memory that is when you
        hibernate has handed the complete object filled with data it has injected.

        1. Getters
        Allows other classes to see what value is inside the safe without touching the variable directly.

        2. Setters
        Allows other classes to slide brand-new values inside to the safe to overwrite the old one
     */


    public Integer getId() {
        return id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = this.classId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*
        The @Override is like a safety sticky note for the Java compiler telling it that you want to write custom rules
        for methods that already exist and are built-in. Every object created inherits from a set of default, built-in
        methods from Java's master blueprint class called the Object class. The default equals() and hashcode() are
        apart of it so the @Override allows us to rewrite the rules for those methods.

        These two methods are here to teach Java how to compare two different Note objects to see if they are identical.
        Java compares memory address by default so two Note objects with the same data are different.


     */

    /*
        This method defines what makes two Note objects "equal" in the real world by force Jave to check the actual
        data inside rather than their memory addresses
     */
    @Override
    public boolean equals(Object o) {
        // If the other object o is empty or isn't a Note object like comparing a Note to a User, instantly return false
        // They are not equal
        if (o == null || getClass() != o.getClass()) return false;

        // Convert the object o to a note object with type casting, without we cant access the note fields because
        // object o is just a generic object under the Object class
        Note note = (Note) o;

        // Check every single field to see if they are the same and if so return true
        return Objects.equals(id, note.id) && Objects.equals(classId, note.classId) &&
                Objects.equals(text, note.text);
    }

    /*
        So whenever we change how equals works, Java rules states we MUST change how hashcode() works. Without changing
        the hashcode() as well, we could have two identical Note objects in terms of data but different digital
        fingerprints. So the two could be put into a HashSet which bans duplicates. Btw, Java uses hashcode fingerprints
        to organize objects into "aisles" inside HashMaps or HashSets. Now there is duplicate data inside which can
        cause bugs and memory leaks

        This method takes all the data inside our Note object and mashes them together to create a single unique
        identification number (a digital fingerprint). If two Note objects have the same text and ID, then they must
        generate the exact same fingerprint number.

     */

    @Override
    public int hashCode() {
        /* Objects.hash() is a digital blender: you throw all four of the private variables into the blender. It mixes
            the values together using a math formula and pours out one single number. If you put the exact same values
            inside the blender tomorrow, it'll pour out the exact same number. But if you change one character, it'll
            pour our a totally different number.

            The number is unique to a specific combination of data inside your object.
         */
        return Objects.hash(id, classId, text);
    }
}
