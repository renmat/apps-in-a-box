CREATE TABLE u123_user (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash BLOB NOT NULL,
    salt BLOB NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE u124_role (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE u125_user_role (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES u123_user(id),
    FOREIGN KEY (role_id) REFERENCES u124_role(id)
);