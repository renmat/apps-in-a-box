CREATE TABLE s123_agent_session (
    id INT PRIMARY KEY AUTO_INCREMENT,
    agent_id VARCHAR(255) NOT NULL,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP,
    status VARCHAR(255) DEFAULT 'ACTIVE'
);

CREATE TABLE s124_agent_activity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    session_id INT,
    activity_type VARCHAR(255),
    activity_data TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES s123_agent_session(id)
);