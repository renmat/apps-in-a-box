package com.github.abx.boot1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.github.abx.common.jpa.db1.model.Agent;
import com.github.abx.common.jpa.db1.repo.AgentRepository;

@Service
public class AgentService {

	@Autowired
	private AgentRepository agentRepository;
	
	
	public Page<Agent> listAgents(int pageNumber, int pageSize) {
		return agentRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}

}
