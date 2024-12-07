package com.github.abx.common.jpa.db3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db3.model.AgentActivity;

@Repository
public interface AgentActivityRepository extends JpaRepository<AgentActivity, Integer> {
    
}

