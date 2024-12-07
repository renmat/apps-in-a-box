package com.github.abx.common.jpa.db1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db1.model.AgentEnvironment;

@Repository
public interface AgentEnvironmentRepository extends JpaRepository<AgentEnvironment, String> {
    
}
