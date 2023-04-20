DROP TABLE IF EXISTS endpoints_hits;

CREATE TABLE endpoints_hits
(
    endpoint_hit_ids int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    app              varchar(512)                                  NOT NULL,
    uri              varchar(512),
    ip               varchar(512),
    timestamp        timestamp                                    NOT NULL
)