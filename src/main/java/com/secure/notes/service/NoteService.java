package com.secure.notes.service;

import com.secure.notes.dto.NotesDto;

import java.util.List;

public interface NoteService {
    NotesDto createNoteForUser(String username, String content);
    NotesDto updateNoteForUser(Long noteId, String content, String username);
    void deleteNoteForUser(Long noteId, String username);
    List<NotesDto> getNotesForUser(String username);
}
