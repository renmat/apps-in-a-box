package com.github.abx.common.jpa.db1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db1.model.Environment;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, String> {
    
}
