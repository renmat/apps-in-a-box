package com.github.abx.common.jpa.db2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db2.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    
}

