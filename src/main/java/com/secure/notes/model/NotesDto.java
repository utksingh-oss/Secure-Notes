package com.secure.notes.model;

import com.secure.notes.entity.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesDto {
    private Long id;
    private String content;
    private String ownerUsername;

    public NotesDto(Note note) {
        this.id = note.getId();
        this.content = note.getContent();
        this.ownerUsername = note.getOwnerUsername();
    }
}
