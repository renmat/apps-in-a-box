package com.github.abx.common.jpa.db2.model;
import java.time.LocalDateTime;
import java.util.Set;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "u123_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    @Lob
    private byte[] passwordHash;
    @Lob
    private byte[] salt;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    @ManyToMany
    @JoinTable(
        name = "u125_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}