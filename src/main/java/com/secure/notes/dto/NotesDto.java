package com.secure.notes.dto;

import com.secure.notes.entity.Note;
import lombok.Data;

@Data
public class NotesDto {
    private Long id;
    private String content;
    private String ownerUsername;

    public NotesDto() {
    }

    public NotesDto(Note note) {
        this.id = note.getId();
        this.content = note.getContent();
        this.ownerUsername = note.getOwnerUsername();
    }
}
