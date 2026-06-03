package com.raheemspence.controller;

import com.raheemspence.model.Note;
import com.raheemspence.service.NoteService;
import org.springframework.security.core.Authentication;
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
    public List<Note> getNotes(Authentication authentication) {
        System.out.println("Logged in user: " + authentication.getName());
        return noteService.getAllNotes();
    }

    @PostMapping("/create-note")
    public Note addNewNote(@RequestBody Note note, Authentication authentication) {
        System.out.println("Creating note for: " + authentication.getName());
        noteService.insertNote(note);
        return note;
    }
}
