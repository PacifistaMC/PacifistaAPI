create table pacifista_support_tickets
(
    id                bigserial primary key,
    created_at        timestamp(6)   not null,
    updated_at        timestamp(6),
    uuid              varchar(255)   not null constraint uk_public_id_pacifista_support_ticket unique,
    object            varchar(255)   not null,
    created_by_name   varchar(255)   not null,
    created_by_id     varchar(255)   not null,
    creation_source   varchar(100)   not null,
    status            varchar(100)   not null,
    type              varchar(100)   not null
);

create table pacifista_support_tickets_messages
(
    id              bigserial primary key,
    created_at      timestamp(6)   not null,
    updated_at      timestamp(6),
    uuid            varchar(255)   not null constraint uk_public_id_pacifista_support_ticket_message unique,
    message         varchar(10000) not null,
    written_by_id   varchar(255)   not null,
    written_by_name varchar(255)   not null,
    ticket_id       bigint         not null constraint link_to_support_ticket_pcfta references pacifista_support_tickets
);
