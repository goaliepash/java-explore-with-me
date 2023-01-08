DROP TABLE IF EXISTS compilations_events;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS categories
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation TEXT NOT NULL,
    category_id BIGINT,
    confirmed_requests INTEGER,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    description TEXT,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT,
    lat REAL,
    lon REAL,
    paid BOOLEAN NOT NULL,
    participant_limit INTEGER,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state VARCHAR(20) NOT NULL,
    title VARCHAR(1000),
    views INTEGER,
    CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_initiator FOREIGN KEY(initiator_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id BIGINT,
    requester_id BIGINT,
    status VARCHAR(20),
    CONSTRAINT fk_event FOREIGN KEY(event_id) REFERENCES events(id),
    CONSTRAINT fk_requester FOREIGN KEY(requester_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT REFERENCES compilations(id) ON DELETE CASCADE NOT NULL,
    events_id BIGINT REFERENCES events(id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT pk_compilation_event PRIMARY KEY (compilation_id, events_id)
);