create table pacifista_box
(
    id               bigint generated by default as identity primary key,
    created_at       timestamp    not null,
    updated_at       timestamp,
    uuid             varchar(255) not null constraint UK_box_public_id unique,
    box_description  varchar(500) not null,
    box_display_name varchar(255) not null,
    box_name         varchar(255) not null constraint UK_box_name unique,
    drop_amount      integer      not null,
    game_mode        varchar(255) not null
);

create table pacifista_box_reward
(
    id              bigint generated by default as identity primary key,
    created_at      timestamp      not null,
    updated_at      timestamp,
    uuid            varchar(255)   not null constraint UK_box_reward_public_id unique,
    item_serialized varchar(50000) not null,
    rarity          real           not null,
    box_id          bigint         not null constraint link_key_box_id references pacifista_box
);

create table pacifista_player_box
(
    id          bigint generated by default as identity primary key,
    created_at  timestamp    not null,
    updated_at  timestamp,
    uuid        varchar(255) not null constraint UK_player_box_public_id unique,
    amount      integer      not null,
    player_uuid varchar(255) not null,
    box_id      bigint       not null constraint link_box_id_player_box references pacifista_box
);
