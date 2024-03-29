create table player_homes
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_player_homes_public_id unique,
    x                                double precision not null,
    y                                double precision not null,
    z                                double precision not null,
    yaw                              double precision not null,
    pitch                            double precision not null,
    world_uuid                       varchar(255)    not null,
    server_type                      varchar(255)    not null,
    name                             varchar(255)    not null,
    player_uuid                      varchar(255)    not null
);
