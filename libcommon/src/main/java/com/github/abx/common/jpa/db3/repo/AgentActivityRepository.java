package com.github.abx.common.jpa.db3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.abx.common.jpa.db3.model.ActivityOutbox;
import com.github.abx.common.jpa.db3.model.AgentActivity;

@Repository
public interface AgentActivityRepository extends JpaRepository<AgentActivity, Integer> {
    
	@Transactional
	default public void saveAgentActivityWithOutboxMsg(AgentActivity activity,ActivityOutbox msg,ActivityOutboxRepository repository) {
		save(activity);
		repository.save(msg);
	}
	
}

