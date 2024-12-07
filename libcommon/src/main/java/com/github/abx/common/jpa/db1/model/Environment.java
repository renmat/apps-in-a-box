package com.github.abx.common.jpa.db1.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "r125_environment")
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String type;
    private String description;
    private LocalDateTime createdAt;
}
