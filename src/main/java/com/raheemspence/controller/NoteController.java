package com.raheemspence.controller;

import com.raheemspence.Note;
import com.raheemspence.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/v2/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/create-note")
    public Note addNewNote(@RequestBody Note note) {
        noteService.insertNote(note);
        return note;
    }
}
