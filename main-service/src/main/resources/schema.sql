DROP TABLE IF EXISTS categories, compilations, events, events_users, locations, users, events_compilations, comments CASCADE;

CREATE TABLE IF NOT EXISTS categories
(
    id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(1024),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    pinned BOOLEAN                                      NOT NULL,
    title  VARCHAR(1024)                                NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id    INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    email VARCHAR(1024)                                    NOT NULL,
    name  VARCHAR(1024)                                    NOT NULL,
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    lat DECIMAL                                          NOT NULL,
    lon DECIMAL                                          NOT NULL
);



CREATE TABLE IF NOT EXISTS events
(
    id                 INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    annotation         VARCHAR(1024)                                NOT NULL,
    category_id        INT REFERENCES categories (id)               NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE                  NOT NULL,
    description        VARCHAR(1024)                                NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE                  NOT NULL,
    initiator_id       INT REFERENCES users (id) ON DELETE CASCADE  NOT NULL,
    location_id        INT REFERENCES locations (id),
    paid               BOOLEAN                                      NOT NULL,
    participant_limit  INT                                          NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                                      NOT NULL,
    state              VARCHAR(1024)                                NOT NULL,
    title              VARCHAR(1024)                                NOT NULL
);

CREATE TABLE IF NOT EXISTS "events_compilations"
(
    event_id       INT REFERENCES events (id) ON DELETE CASCADE        NOT NULL,
    compilation_id INT REFERENCES compilations (id) ON DELETE RESTRICT NOT NULL,
    UNIQUE (event_id, compilation_id)
);

CREATE TABLE IF NOT EXISTS events_users
(
    id           INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    event_id     INT REFERENCES events (id) ON DELETE CASCADE     NOT NULL,
    requester_id INT REFERENCES users (id) ON DELETE CASCADE      NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    status       VARCHAR(1024)                                    NOT NULL,
    UNIQUE (event_id, requester_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id         INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    user_id    INT REFERENCES users (id)                        NOT NULL,
    event_id   INT REFERENCES events (id) ON DELETE CASCADE     NOT NULL,
    text       VARCHAR                                          NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    edited_on  TIMESTAMP WITHOUT TIME ZONE
);
