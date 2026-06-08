package com.raheemspence.repository;

import com.raheemspence.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwnerId(Long ownerId);

    List<Note> findByCourseId(Long courseId);
}
