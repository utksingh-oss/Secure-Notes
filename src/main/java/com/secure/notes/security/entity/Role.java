package com.secure.notes.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.secure.notes.security.enums.AppRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private AppRole roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @ToString.Exclude
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Role(AppRole roleName){
        this.roleName = roleName;
    }
}
