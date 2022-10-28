create table pacifista_box
(
    id               bigint auto_increment primary key,
    created_at       datetime(6)  not null,
    updated_at       datetime(6)  null,
    uuid             varchar(255) not null,
    box_name         varchar(255) not null,
    box_display_name varchar(255) not null,
    box_description  varchar(500) not null,
    drop_amount      int          not null,
    game_mode        varchar(255) not null,
    constraint UK_box_name unique (box_name),
    constraint UK_box_public_id unique (uuid)
);

create table pacifista_box_reward
(
    id              bigint auto_increment primary key,
    created_at      datetime(6)   not null,
    updated_at      datetime(6)   null,
    uuid            varchar(255)  not null,
    item_serialized varchar(10000) not null,
    rarity          float         not null,
    box_id          bigint        not null,
    constraint UK_box_reward_public_id unique (uuid),
    constraint link_key_box_id foreign key (box_id) references pacifista_box (id)
);

create table pacifista_player_box
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  not null,
    updated_at  datetime(6)  null,
    uuid        varchar(255) not null,
    amount      int          not null,
    player_uuid varchar(255) not null,
    box_id      bigint       not null,
    constraint UK_player_box_public_id unique (uuid),
    constraint link_box_id_player_box foreign key (box_id) references pacifista_box (id)
);

create table pacifista_guilds
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  not null,
    updated_at  datetime(6)  null,
    uuid        varchar(255) not null,
    description varchar(255) not null,
    money       double       not null,
    name        varchar(255) not null,
    constraint UK_guilds_public_id unique (uuid)
);

create table pacifista_guilds_experiences
(
    id         bigint auto_increment primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  null,
    uuid       varchar(255) not null,
    experience int          not null,
    level      int          not null,
    guild_id   bigint       not null,
    constraint UK_guilds_exp_public_id unique (uuid),
    constraint link_key_guild_id_exp foreign key (guild_id) references pacifista_guilds (id)
);

create table pacifista_guilds_homes
(
    id            bigint auto_increment primary key,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  null,
    uuid          varchar(255) not null,
    pitch         float        not null,
    server_type   varchar(255) not null,
    world_uuid    varchar(255) not null,
    x             double       not null,
    y             double       not null,
    yaw           float        not null,
    z             double       not null,
    public_access bit          not null,
    guild_id      bigint       not null,
    constraint UK_guild_home_public_id unique (uuid),
    constraint link_key_guild_id_home foreign key (guild_id) references pacifista_guilds (id)
);

create table pacifista_guilds_logs
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)   not null,
    updated_at  datetime(6)   null,
    uuid        varchar(255)  not null,
    action      varchar(5000) not null,
    player_uuid varchar(255)  not null,
    guild_id    bigint        not null,
    constraint UK_guild_logs_public_id unique (uuid),
    constraint link_key_guild_id_logs foreign key (guild_id) references pacifista_guilds (id)
);

create table pacifista_guilds_members
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  not null,
    updated_at  datetime(6)  null,
    uuid        varchar(255) not null,
    player_uuid varchar(255) not null,
    role        varchar(255) not null,
    guild_id    bigint       not null,
    constraint UK_guild_members_public_id unique (uuid),
    constraint link_key_guild_id_members foreign key (guild_id) references pacifista_guilds (id)
);

create table pacifista_guilds_messages
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)   not null,
    updated_at  datetime(6)   null,
    uuid        varchar(255)  not null,
    message     varchar(5000) not null,
    player_uuid varchar(255)  not null,
    subject     varchar(255)  not null,
    guild_id    bigint        not null,
    constraint UK_guild_messages_public_id unique (uuid),
    constraint link_key_guild_id_message foreign key (guild_id) references pacifista_guilds (id)
);

create table pacifista_role
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    uuid              varchar(255) not null,
    name              varchar(255) not null,
    player_name_color varchar(255) not null,
    power             varchar(255) not null,
    prefix            varchar(255) not null,
    staff_rank        bit          not null,
    constraint UK_role_public_id unique (uuid),
    constraint UK_name_role_unique unique (name)
);

create table pacifista_permission
(
    id         bigint auto_increment primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  null,
    uuid       varchar(255) not null,
    permission varchar(255) not null,
    role_id    bigint       not null,
    constraint UK_permission_public_id unique (uuid),
    constraint link_permission_to_role_id foreign key (role_id) references pacifista_role (id)
);

create table pacifista_player_role
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  not null,
    updated_at  datetime(6)  null,
    uuid        varchar(255) not null,
    player_uuid varchar(255) not null,
    role_id     bigint       not null,
    constraint UK_player_role_public_id unique (uuid),
    constraint link_key_role_id_player_role foreign key (role_id) references pacifista_role (id)
);

create table pacifista_player_enderchest_data
(
    id                               bigint auto_increment primary key,
    created_at                       datetime(6)  not null,
    updated_at                       datetime(6)  null,
    uuid                             varchar(255) not null,
    enderchest_elite_serialized      longtext     not null,
    enderchest_legendaire_serialized longtext     not null,
    enderchest_mine_serialized       longtext     not null,
    enderchest_paladin_serialized    longtext     not null,
    enderchest_serialized            longtext     not null,
    game_mode                        varchar(255) not null,
    player_owner_uuid                varchar(255) not null,
    constraint UK_player_enderchest_data_public_id unique (uuid)
);

create table pacifista_player_experience_data
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    uuid              varchar(255) not null,
    exp               float        not null,
    game_mode         varchar(255) not null,
    level             int          not null,
    player_owner_uuid varchar(255) not null,
    total_experience  int          not null,
    constraint UK_player_experience_data_public_id unique (uuid)
);

create table pacifista_player_inventory_data
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    uuid              varchar(255) not null,
    armor             longtext     not null,
    game_mode         varchar(255) not null,
    inventory         longtext     not null,
    player_owner_uuid varchar(255) not null,
    constraint UK_player_inventory_data_public_id unique (uuid)
);

create table pacifista_player_money_data
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    uuid              varchar(255) not null,
    money             double       not null,
    player_owner_uuid varchar(255) not null,
    constraint UK_player_money_data_public_id unique (uuid)
);

create table pacifista_sanctions
(
    id                   bigint auto_increment primary key,
    created_at           datetime(6)  not null,
    updated_at           datetime(6)  null,
    uuid                 varchar(255) not null,
    active               bit          not null,
    expiration_date      datetime(6)  null,
    ip_sanction          bit          not null,
    player_action_ip     varchar(255) null,
    player_action_uuid   varchar(255) null,
    player_sanction_ip   varchar(255) not null,
    player_sanction_uuid varchar(255) not null,
    reason               varchar(255) not null,
    sanction_type        varchar(255) not null,
    constraint UK_sanctions_public_id unique (uuid)
);

create table pacifista_warps
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  not null,
    updated_at        datetime(6)  null,
    uuid              varchar(255) not null,
    pitch             float        not null,
    server_type       varchar(255) not null,
    world_uuid        varchar(255) not null,
    x                 double       not null,
    y                 double       not null,
    yaw               float        not null,
    z                 double       not null,
    name              varchar(255) not null,
    player_owner_uuid varchar(255) not null,
    public_access     bit          not null,
    type              varchar(255) not null,
    warp_item         varchar(255) not null,
    constraint UK_warps_public_id unique (uuid)
);
