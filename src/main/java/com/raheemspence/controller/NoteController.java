package com.raheemspence.controller;

import com.raheemspence.dto.NoteRequest;
import com.raheemspence.dto.NoteResponse;
import com.raheemspence.service.NoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteResponse> getNotes(HttpSession httpSession) {

        // Get user by id
        Long userId = (Long) httpSession.getAttribute("userId");

        if (userId == null) {
            return List.of();
        }
        return noteService.getNotesByUserId(userId);
    }

    /*
        @RequestBody tells Spring to take the incoming HTTP request JSON body and convert it into a Java object (dto)
     */
    @PostMapping("/create")
    public NoteResponse createNote(@RequestBody NoteRequest noteRequest, HttpSession httpSession) {
        // Get user id
        Long userId = (Long) httpSession.getAttribute("userId");

        return noteService.createNote(userId, noteRequest);

    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, HttpSession httpSession) {
        System.out.println("DELETE HIT");
        System.out.println("SESSION ID: " + httpSession.getId());
        System.out.println("USER ID: " + httpSession.getAttribute("userId"));

        // Get user id
        Long userId = (Long) httpSession.getAttribute("userId");

        noteService.deleteNote(userId, noteId);
    }
}
