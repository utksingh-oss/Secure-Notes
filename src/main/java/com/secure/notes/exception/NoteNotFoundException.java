package com.secure.notes.exception;

public class NoteNotFoundException extends RuntimeException {
    private static final String NOTE_WITH_ID_NOT_FOUND =
            "Note with id: %s, not found";

    public NoteNotFoundException(Long noteId) {
        super(String.format(NOTE_WITH_ID_NOT_FOUND, noteId));
    }
}
