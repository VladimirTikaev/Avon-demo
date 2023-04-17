create table order_status
(
    id        bigserial primary key,
    name      varchar(30) not null unique
);

create table client_order
(
    id                  bigserial primary key,
    client_id bigint    not null references client,
    status_id bigint    not null references order_status,
    date_order          timestamp with time zone,
);

create table product
(
     id                bigserial primary key,
     order_id bigint   not null references client_order,
     product_code      varchar(30) not null unique
     prouct_count      int,
     price             bigint
);

create table client_type
(
    id        bigserial primary key,
    name      varchar(30) not null unique
);

create table remuneration_level
(
    id        bigserial primary key,
    name      varchar(30) not null unique
);

create table client
(
    id        bigserial primary key,
    type_id   bigint not null references client_type,
    level_id  bigint not null references remuneration_level,
    fio       varchar(1000),
    discount  real
);


