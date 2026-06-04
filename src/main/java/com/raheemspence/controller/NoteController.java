package com.raheemspence.controller;

import com.raheemspence.dto.NoteRequest;
import com.raheemspence.dto.NoteResponse;
import com.raheemspence.service.NoteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

        @Valid tells Spring that before calling my method, validate the request body using annotations like @NotBlank
     */
    @PostMapping("/create")
    public NoteResponse createNote(@Valid @RequestBody NoteRequest noteRequest, HttpSession httpSession) {
        // Get user id
        Long userId = (Long) httpSession.getAttribute("userId");

        return noteService.createNote(userId, noteRequest);

    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, HttpSession httpSession) {
        // Get user id
        Long userId = (Long) httpSession.getAttribute("userId");

        noteService.deleteNote(userId, noteId);
    }

    /*
        @PathVariable takes data from the URL and passes it into a method parameter
     */
    @PutMapping("/{noteId}")
    public NoteResponse updateNote(@PathVariable Long noteId,
                                  @Valid @RequestBody NoteRequest noteRequest,
                                   HttpSession httpSession) {
        // Get user id from session
        Long userId = (Long) httpSession.getAttribute("userId");

        return noteService.updateNote(userId, noteId, noteRequest);
    }
}
