drop table if exists order_status;
create table order_status
(
    id   bigserial primary key,
    name varchar(30) not null unique
);

drop table if exists client_type;
create table client_type
(
    id   bigserial primary key,
    name varchar(30) not null unique
);

drop table if exists remuneration_level;
create table remuneration_level
(
    id          bigserial primary key,
    name        varchar(30) not null unique,
    extra_bonus real        not null
);

drop table if exists client;
create table client
(
    id       bigserial primary key,
    type_id  bigint not null references client_type,
    level_id bigint not null references remuneration_level,
    fio      varchar(1000),
    discount real
);

drop table if exists client_order;
create table client_order
(
    id         bigserial primary key,
    client_id  bigint not null references client,
    status_id  bigint not null references order_status,
    date_order timestamp with time zone
);

drop table if exists product;
create table product
(
    id            bigserial primary key,
    order_id      bigint      not null references client_order,
    product_code  varchar(30) not null unique,
    product_count int,
    price         bigint
);

drop table if exists client_connection;
create table client_connection
(
    id          bigserial primary key,
    parent_id   bigint not null references client,
    child_id    bigint not null references client,
    date_create timestamp with time zone
);








