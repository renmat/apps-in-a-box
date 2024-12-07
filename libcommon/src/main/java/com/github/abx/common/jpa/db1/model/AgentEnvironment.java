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
@Table(name = "r126_agent_environment")
public class AgentEnvironment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @ManyToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;
    private LocalDateTime interactionTimestamp;
    private String interactionData;
}