package com.github.abx.common.jpa.db1.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "r124_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    private String status;
    private LocalDateTime createdAt;
}
