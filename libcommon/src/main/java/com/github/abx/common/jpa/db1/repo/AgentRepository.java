package com.github.abx.common.jpa.db1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db1.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {
    
}