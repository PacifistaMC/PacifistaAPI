create table pacifista_news
(
    id                bigserial primary key,
    created_at        timestamp(6)   not null,
    updated_at        timestamp(6),
    uuid              varchar(255)   not null constraint uk_public_id_pacifista_news unique,
    article_image_url varchar(2000)  not null,
    body              varchar(20000) not null,
    name              varchar(255)   not null,
    original_writer   varchar(255)   not null,
    subtitle          varchar(255)   not null,
    title             varchar(255)   not null,
    update_writer     varchar(255)
);
