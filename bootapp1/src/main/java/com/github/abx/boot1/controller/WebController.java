package com.github.abx.boot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abx.boot1.service.AgentService;
import com.github.abx.common.jpa.db1.model.Agent;

@Controller
@RequestMapping("/")
public class WebController {

	@Autowired
	private AgentService agentService;
	
	@GetMapping("/agents")
	public @ResponseBody Page<Agent> listAgents(){
		return agentService.listAgents(0,10);
	}
	
}
