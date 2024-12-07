package com.github.abx.common.jpa.db3.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "s123_agent_session")
public class AgentSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String agentId;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
    private String status;
}