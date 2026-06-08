package com.raheemspence.service;

import com.raheemspence.dto.NoteRequest;
import com.raheemspence.dto.NoteResponse;
import com.raheemspence.model.Course;
import com.raheemspence.model.Note;
import com.raheemspence.model.User;
import com.raheemspence.repository.CourseRepository;
import com.raheemspence.repository.NoteRepository;
import com.raheemspence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository,
                       CourseRepository courseRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<NoteResponse> getNotesByUserId(Long userId) {
        // Get notes as a list
        List<Note> notes = noteRepository.findByOwnerId(userId);

        // Create new list to store NoteResponse dto's
        List<NoteResponse> noteResponseList = new ArrayList<>();

        // Loop through list of notes and retrieve fields to populate NoteResponse dto's
        for (Note note: notes) {
            Long id = note.getId();
            String title = note.getTitle();
            String content = note.getContent();
            Instant createdAt = note.getCreatedAt();
            Long ownerId = note.getOwner().getId();
            String username = note.getOwner().getUsername();

            // Create NoteResponse dto
            NoteResponse noteResponse = new NoteResponse();

            // Populate dto
            noteResponse.setId(id);
            noteResponse.setTitle(title);
            noteResponse.setContent(content);
            noteResponse.setCreatedAt(createdAt);
            noteResponse.setOwnerId(ownerId);
            noteResponse.setOwnerUsername(username);

            // Add dto to list
            noteResponseList.add(noteResponse);
        }

        return noteResponseList;
    }

    public NoteResponse createNote(Long userId, Long courseId, NoteRequest noteRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found")
                );

        Note note = new Note();

        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setOwner(user);
        note.setCourse(course);


        Note savedNote = noteRepository.save(note);

        NoteResponse noteResponse = new NoteResponse();

        noteResponse.setTitle(savedNote.getTitle());
        noteResponse.setContent(savedNote.getContent());
        noteResponse.setOwnerId(userId);
        noteResponse.setOwnerUsername(user.getUsername());
        noteResponse.setCreatedAt(savedNote.getCreatedAt());
        noteResponse.setId(savedNote.getId());
        noteResponse.setCourseId(course.getId());
        noteResponse.setCourseName(course.getName());

        return noteResponse;

    }

    public void deleteNote(Long userId, Long noteId) {
        // Get note else throw exception 404 NOT FOUND
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        // Check if user is allowed to delete note else throw 403 FORBIDDEN
        if (!note.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this note"
            );
        }
        noteRepository.delete(note);
    }

    public NoteResponse updateNote(Long userId, Long noteId, NoteRequest noteRequest) {
        // Get note else throw exception 404 NOT FOUND
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        // Check if user is allowed to update note else throw 403 FORBIDDEN
        if (!note.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "You are not allowed to update this note"
            );
        }

        // Set title and content from NoteRequest
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());

        Note savedNote = noteRepository.save(note);

        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(savedNote.getId());
        noteResponse.setTitle(savedNote.getTitle());
        noteResponse.setContent(savedNote.getContent());
        noteResponse.setOwnerId(savedNote.getOwner().getId());
        noteResponse.setOwnerUsername(savedNote.getOwner().getUsername());
        noteResponse.setCreatedAt(savedNote.getCreatedAt());


        return noteResponse;
    }
}


