create table pacifista_web_legals
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_pacifista_web_legals_public_id unique,
    content_html                     varchar(100000) not null,
    type                             varchar(255)    not null
);

create table pacifista_web_legals_users
(
    id                               bigint generated by default as identity primary key,
    created_at                       timestamp       not null,
    updated_at                       timestamp,
    uuid                             varchar(255)    not null constraint UK_pacifista_web_legals_users_public_id unique,
    user_id                          varchar(255)    not null,
    type                             varchar(255)    not null
);
