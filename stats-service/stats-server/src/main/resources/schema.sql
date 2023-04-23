DROP TABLE IF EXISTS endpoint_hits;

CREATE TABLE endpoint_hits
(
    endpoint_hit_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    app             varchar(512)                                 NOT NULL,
    uri             varchar(512),
    ip              varchar(512),
    timestamp       timestamp                                    NOT NULL
)