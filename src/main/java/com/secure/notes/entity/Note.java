package com.secure.notes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
//    @Column(columnDefinition = "TEXT")    // for large Strings
    private String content;

    private String ownerUsername;
}
