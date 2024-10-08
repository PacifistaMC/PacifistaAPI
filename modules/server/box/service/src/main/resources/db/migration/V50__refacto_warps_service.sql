drop table if exists pacifista_warps;

create table pacifista_warps
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_pacifista_warps_public_id unique,
    name                             varchar(255)     not null,
    json_formatted_description       varchar(10000)   not null,
    warp_item                        varchar(255)     not null,
    player_owner_uuid                varchar(255)     not null,
    type                             varchar(255)     not null,
    uses                             integer          not null,
    likes                            integer          not null,
    x                                double precision not null,
    y                                double precision not null,
    z                                double precision not null,
    yaw                              double precision not null,
    pitch                            double precision not null,
    world_uuid                       varchar(255)     not null,
    server_type                      varchar(255)     not null
);

create table pacifista_warps_config
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_pacifista_warps_config_public_id unique,
    warp_id                          bigint           not null constraint link_key_pacifista_warps_id_configs references pacifista_warps,
    is_visible_in_menu               boolean          not null,
    public_access                    boolean          not null,
    is_free_to_use                   boolean          not null,
    price                            double precision not null
);

create table pacifista_warps_players_interactions
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_pacifista_warps_players_interactions_public_id unique,
    warp_id                          bigint           not null constraint link_key_pacifista_warps_id_interactions references pacifista_warps,
    player_id                        varchar(255)     not null,
    interaction_type                 varchar(255)     not null
);

create table pacifista_warps_portals
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_pacifista_warps_portals_public_id unique,
    warp_id                          bigint           not null constraint link_key_pacifista_warps_id_portals references pacifista_warps,
    server_type                      varchar(255)     not null,
    world_id                         varchar(255)     not null,
    lesser_boundary_corner_x         integer          not null,
    lesser_boundary_corner_y         integer          not null,
    lesser_boundary_corner_z         integer          not null,
    greater_boundary_corner_x        integer          not null,
    greater_boundary_corner_y        integer          not null,
    greater_boundary_corner_z        integer          not null
);
