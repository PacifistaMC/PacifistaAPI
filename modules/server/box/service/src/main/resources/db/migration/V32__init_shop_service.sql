create table admin_shop_category
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_admin_shop_category_public_id unique,
    name                             varchar(255)     not null constraint UK_admin_shop_category_name unique,
    money_sell_limit                 double precision not null
);

create table admin_shop_item
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_admin_shop_item_public_id unique,
    material                         varchar(255)     not null,
    admin_shop_category_id           bigint           not null constraint FK_admin_shop_item_category_id references admin_shop_category,
    price                            double precision not null
);

create table admin_shop_player_limit
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_admin_shop_player_limit_public_id unique,
    player_id                        varchar(255)     not null,
    admin_shop_category_id           bigint           not null constraint FK_admin_shop_player_limit_category_id references admin_shop_category,
    moneyGenerated                   double precision not null
);

create table player_chest_shop
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_player_chest_shop_public_id unique,
    x                                double precision not null,
    y                                double precision not null,
    z                                double precision not null,
    yaw                              double precision not null,
    pitch                            double precision not null,
    world_uuid                       varchar(255)    not null,
    player_id                        varchar(255)    not null,
    item_serialized                  varchar(10000)  not null,
    price                            double precision not null
);

create table player_shop_item
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp        not null,
    updated_at                       timestamp,
    uuid                             varchar(255)     not null constraint UK_player_shop_item_public_id unique,
    minecraft_uuid                   varchar(50)     not null,
    minecraftUsername                varchar(200)     not null,
    sold_at                          timestamp,
    item_serialized                  varchar(10000)   not null,
    price                            double precision not null
);