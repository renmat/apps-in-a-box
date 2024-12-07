CREATE TABLE p123_payment_type (
    code VARCHAR(255) PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE p124_payment_status (
    code VARCHAR(255) PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE p125_payment_provider (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE p126_payment (
    id VARCHAR(36) NOT NULL DEFAULT RANDOM_UUID() PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    payment_method VARCHAR(255),
    payment_status VARCHAR(255),
    payment_provider VARCHAR(255),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (payment_method) REFERENCES p123_payment_type(code),
    FOREIGN KEY (payment_status) REFERENCES p124_payment_status(code),
    FOREIGN KEY (payment_provider) REFERENCES p125_payment_provider(code)
);

CREATE TABLE p127_payment_audit (
    id UUID,
    audit_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(36) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    payment_method VARCHAR(255),
    payment_status VARCHAR(255),
    payment_provider VARCHAR(255),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, audit_timestamp)
);