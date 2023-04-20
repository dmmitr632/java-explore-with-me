DROP TABLE IF EXISTS endpoints_hits;

CREATE TABLE endpoints_hits
(
    endpoint_hit_ids int GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    app              varchar(50)                                  NOT NULL,
    uri              varchar(256),
    ip               varchar(50),
    timestamp        timestamp                                    NOT NULL
)