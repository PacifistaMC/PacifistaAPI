create table pacifista_role
(
    id                bigint generated by default as identity primary key,
    created_at        timestamp    not null,
    updated_at        timestamp,
    uuid              varchar(255) not null constraint UK_role_public_id unique,
    name              varchar(255) not null constraint UK_name_role_unique unique,
    player_name_color varchar(255) not null,
    power             varchar(255) not null,
    prefix            varchar(255) not null,
    staff_rank        boolean      not null
);

create table pacifista_permission
(
    id         bigint generated by default as identity primary key,
    created_at timestamp    not null,
    updated_at timestamp,
    uuid       varchar(255) not null constraint UK_permission_public_id unique,
    permission varchar(255) not null,
    role_id    bigint       not null constraint link_permission_to_role_id references pacifista_role
);

create table pacifista_player_role
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp    not null,
    updated_at  timestamp,
    uuid        varchar(255) not null constraint UK_player_role_public_id unique,
    player_uuid varchar(255) not null,
    role_id     bigint       not null constraint link_key_role_id_player_role references pacifista_role
);
