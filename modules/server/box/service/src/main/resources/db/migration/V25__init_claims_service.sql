create table claim_data
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_claim_data_public_id unique,
    parent_id                        bigint          constraint link_key_parent_id_claim_claim_data references claim_data,
    server_type                      varchar(255)    not null,
    world_id                         varchar(255)    not null,
    lesser_boundary_corner_x         double precision not null,
    lesser_boundary_corner_z         double precision not null,
    greater_boundary_corner_x        double precision not null,
    greater_boundary_corner_z        double precision not null
);

create table claim_data_config
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_claim_data_config_public_id unique,
    claim_id                         bigint          not null constraint link_key_claim_data_id_claim_data_config references claim_data,
    explosion_enabled                boolean         not null,
    fire_spread_enabled              boolean         not null,
    mob_griefing_enabled             boolean         not null,
    pvp_enabled                      boolean         not null,
    public_access                    boolean         not null,
    public_interact_buttons          boolean         not null,
    public_interact_doors_trap_doors boolean         not null,
    public_interact_chests           boolean         not null,
    animal_protection                boolean         not null,
    grief_protection                 boolean         not null
);

create table claim_data_user
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_claim_data_user_public_id unique,
    claim_id                         bigint          not null constraint link_key_claim_data_id_claim_data_user references claim_data,
    player_id                        varchar(255)    not null,
    role                             varchar(255)    not null
);

create table user_claim_amount
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_user_claim_amount_public_id unique,
    player_id                        varchar(255)    not null,
    blocksAmount                     integer         not null
);
