CREATE TABLE clients (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    creation_time TIMESTAMP NOT NULL,
    deactivation_time TIMESTAMP

);

CREATE TABLE liabilities_of_clients (
    id BIGINT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    dateStart date NOT NULL,
    deactivated TIMESTAMP,
    frequency TEXT,
    reminder_time_days INT NOT NULL DEFAULT (7),
    CONSTRAINT fk_liability_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
);

CREATE TABLE liabilities_single(
    id BIGINT PRIMARY KEY ,
    liability_id BIGINT NOT NULL,
    name TEXT NOT NULL,
    deadline TIMESTAMP NOT NULL,
    date_completion TIMESTAMP,
    CONSTRAINT fk_liabilities
        FOREIGN KEY (liability_id)
            REFERENCES liabilities_of_clients(id)
)



