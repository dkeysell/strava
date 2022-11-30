CREATE SEQUENCE hibernate_sequence start with 35;

CREATE TABLE grid
(
    id      SERIAL PRIMARY KEY,
    sw_lat  numeric(5, 2) NOT NULL,
    sw_long numeric(5, 2) NOT NULL,
    UNIQUE (sw_lat, sw_long)
);

CREATE TABLE segment
(
    id                integer        PRIMARY KEY,
    grid_id           integer        NOT NULL REFERENCES grid (id),
    name              varchar(100)    NOT NULL,
    distance          numeric(9,3)   NOT NULL,
    average_grade     numeric(3,1),
    resource_state    smallint       NOT NULL,
    activity_type     varchar(10),
    climb_category    smallint       NOT NULL,
    maximum_grade     numeric(5,2),
    elevation_high    numeric(6,1),
    elevation_low     numeric(6,1),
    startLat          numeric(17,14) NOT NULL,
    startLong         numeric(17,14) NOT NULL,
    endLat            numeric(17,14) NOT NULL,
    endLong           numeric(17,14) NOT NULL,
    city              varchar(50),
    state             varchar(50),
    country           varchar(50),
    private           boolean,
    hazardous         boolean,
    elevation_profile varchar(300)   NOT NULL,
    effort_count	  integer,
    athlete_count	  integer,
    kom               varchar(30),
    qom               varchar(30),
    kom_watts         numeric(9,2),
    qom_watts         numeric(9,2)
);
