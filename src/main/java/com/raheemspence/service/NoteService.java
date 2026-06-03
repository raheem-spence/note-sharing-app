package com.raheemspence.service;

import com.raheemspence.model.Note;
import com.raheemspence.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note insertNote(Note note) {
        noteRepository.save(note);
        return note;
    }
}
