package com.secure.notes.service.impl;

import com.secure.notes.dto.NotesDto;
import com.secure.notes.exception.NoteNotFoundException;
import com.secure.notes.entity.Note;
import com.secure.notes.repository.NoteRepository;
import com.secure.notes.service.NoteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public NotesDto createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        Note savedNote = noteRepository.save(note);
        return new NotesDto(savedNote);
    }

    @Override
    public NotesDto updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new NoteNotFoundException(noteId)
        );
        note.setContent(content);
        Note savedNote = noteRepository.save(note);
        return new NotesDto(savedNote);
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        noteRepository.deleteById(noteId);
    }

    @Override
    @Transactional
    public List<NotesDto> getNotesForUser(String username) {
        List<Note> notes = noteRepository.findByOwnerUsername(username);
        return notes.stream().map(NotesDto::new).toList();
    }
}
