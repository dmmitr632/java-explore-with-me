DROP TABLE IF EXISTS hits;
DROP TABLE IF EXISTS comments cascade;
DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS bookings cascade;
DROP TABLE IF EXISTS items cascade;
DROP TABLE IF EXISTS users cascade;

CREATE TABLE hits
(
    id        int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    app       varchar(50)                                         NOT NULL,
    uri       varchar(256),
    ip        varchar(50),
    timestamp timestamp                                           NOT NULL
)