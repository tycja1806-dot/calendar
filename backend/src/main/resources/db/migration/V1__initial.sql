CREATE TABLE "clients"
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name              TEXT      NOT NULL,
    creation_date     DATE NOT NULL,
    deactivation_date DATE

);

CREATE TABLE "liabilities-of-clients"
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name               TEXT      NOT NULL,
    client_id          BIGINT    NOT NULL,
    start_date         DATE NOT NULL,
    deactivated_date   DATE,
    frequency          SMALLINT,
    reminder_time_days INT       NOT NULL DEFAULT (7),
    CONSTRAINT fk_liability_client
        FOREIGN KEY (client_id)
            REFERENCES clients (id)
);

CREATE TABLE "liabilities-single"
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    liability_id    BIGINT    NOT NULL,
    name            TEXT      NOT NULL,
    deadline        TIMESTAMP NOT NULL,
    date_completion TIMESTAMP,
    CONSTRAINT fk_liabilities
        FOREIGN KEY (liability_id)
            REFERENCES "liabilities-of-clients" (id)
)



