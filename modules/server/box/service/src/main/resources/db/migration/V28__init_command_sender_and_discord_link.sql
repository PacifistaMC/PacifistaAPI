create table command_to_send
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_command_to_send_public_id unique,
    command                          varchar(255)    not null,
    server_type                      varchar(255)    not null,
    is_command_for_proxy             boolean         not null,
    executed                         boolean         not null,
    creation_canal                   varchar(255)    not null
);

create table discord_link
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_discord_link_public_id unique,
    discord_user_id                  varchar(255)    not null constraint UK_discord_link_discord_user_id unique,
    minecraft_uuid                   varchar(255)    not null constraint UK_discord_link_minecraft_uuid unique,
    is_linked                        boolean         not null
);

