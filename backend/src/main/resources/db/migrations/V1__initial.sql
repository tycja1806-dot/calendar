CREATE TABLE clients (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    creation_time TIMESTAMP NOT NULL,
    deactivation_time TIMESTAMP
);

CREATE TABLE liabilities_of_clients (
    id BIGINT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    name TEXT NOT NULL,
    deadline TIMESTAMP NOT NULL,
    reminder_time_days INT NOT NULL DEFAULT (7),
    deactivated TIMESTAMP,
    frequency TEXT,
    CONSTRAINT fk_liability_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
);




