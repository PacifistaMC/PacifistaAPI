create table shop_categories
(
    id         bigserial primary key,
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    uuid       varchar(255) not null constraint uk_public_shop_categories_id unique,
    name       varchar(255) not null
);

create table shop_articles
(
    id               bigserial primary key,
    created_at       timestamp(6)     not null,
    updated_at       timestamp(6),
    uuid             varchar(255)     not null constraint uk_public_id_shop_articles unique,
    description      varchar(255)     not null,
    html_description varchar(10000)   not null,
    logo_url         varchar(255)     not null,
    name             varchar(255)     not null,
    price            double precision not null,
    category_id      bigint           not null constraint category_id_link_to_article references shop_categories
);
