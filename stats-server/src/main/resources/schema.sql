DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app VARCHAR(1000),
    uri VARCHAR(1000),
    ip VARCHAR(200),
    timestamp TIMESTAMP WITHOUT TIME ZONE
);