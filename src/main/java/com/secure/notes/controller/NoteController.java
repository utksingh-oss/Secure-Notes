package com.secure.notes.controller;


import com.secure.notes.dto.NotesDto;
import com.secure.notes.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.secure.notes.constant.ControllerConstant.USERNAME;

@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public NotesDto createNote(@RequestBody String content,
                               @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logUserDetails(userDetails);
        return noteService.createNoteForUser(username, content);
    }

    @GetMapping
    public List<NotesDto> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logUserDetails(userDetails);
        return noteService.getNotesForUser(username);
    }

    @PutMapping("/{noteId}")
    public NotesDto updateNote(@PathVariable Long noteId,
                               @RequestBody String content,
                               @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logUserDetails(userDetails);
        return noteService.updateNoteForUser(noteId, content, username);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logUserDetails(userDetails);
        noteService.deleteNoteForUser(noteId, username);
    }

    private void logUserDetails(UserDetails userDetails) {
        log.info(USERNAME, userDetails.getUsername());
    }
}
