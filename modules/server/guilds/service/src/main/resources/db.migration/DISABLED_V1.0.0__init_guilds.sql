create table pacifista_guilds
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp        not null,
    updated_at  timestamp,
    uuid        varchar(255)     not null constraint UK_guilds_public_id unique,
    description varchar(255)     not null,
    money       double precision not null,
    name        varchar(255)     not null
);

create table pacifista_guilds_experiences
(
    id         bigint generated by default as identity primary key,
    created_at timestamp    not null,
    updated_at timestamp,
    uuid       varchar(255) not null constraint UK_guilds_exp_public_id unique,
    experience integer      not null,
    level      integer      not null,
    guild_id   bigint       not null constraint link_key_guild_id_exp references pacifista_guilds
);

create table pacifista_guilds_homes
(
    id            bigint generated by default as identity primary key,
    created_at    timestamp        not null,
    updated_at    timestamp,
    uuid          varchar(255)     not null constraint UK_guild_home_public_id unique,
    pitch         real             not null,
    server_type   varchar(255)     not null,
    world_uuid    varchar(255)     not null,
    x             double precision not null,
    y             double precision not null,
    yaw           real             not null,
    z             double precision not null,
    public_access boolean          not null,
    guild_id      bigint           not null constraint link_key_guild_id_home references pacifista_guilds
);

create table pacifista_guilds_logs
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp     not null,
    updated_at  timestamp,
    uuid        varchar(255)  not null constraint UK_guild_logs_public_id unique,
    action      varchar(5000) not null,
    player_uuid varchar(255)  not null,
    guild_id    bigint        not null constraint link_key_guild_id_logs references pacifista_guilds
);

create table pacifista_guilds_members
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp    not null,
    updated_at  timestamp,
    uuid        varchar(255) not null constraint UK_guild_members_public_id unique,
    player_uuid varchar(255) not null,
    role        varchar(255) not null,
    guild_id    bigint       not null constraint link_key_guild_id_members references pacifista_guilds
);

create table pacifista_guilds_messages
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp     not null,
    updated_at  timestamp,
    uuid        varchar(255)  not null constraint UK_guild_messages_public_id unique,
    message     varchar(5000) not null,
    player_uuid varchar(255)  not null,
    subject     varchar(255)  not null,
    guild_id    bigint        not null constraint link_key_guild_id_message references pacifista_guilds
);
