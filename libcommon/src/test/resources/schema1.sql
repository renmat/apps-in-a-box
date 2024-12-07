CREATE TABLE r123_agent (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE r124_task (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    agent_id INT,
    status VARCHAR(255) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agent_id) REFERENCES r123_agent(id)
);

CREATE TABLE r125_environment (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE r126_agent_environment (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    agent_id INT,
    environment_id INT,
    interaction_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    interaction_data TEXT,
    FOREIGN KEY (agent_id) REFERENCES r123_agent(id),
    FOREIGN KEY (environment_id) REFERENCES r125_environment(id)
);