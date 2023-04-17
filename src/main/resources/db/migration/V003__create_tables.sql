drop table if exists client_sales;
create table client_sales
(
    id                 bigserial primary key,
    company_name       varchar(30) not null,
    version            int         not null,
    client_id          bigint references avon.client,
    client_fio         varchar(30) not null,
    client_type        varchar(30) not null,
    reward_level       varchar(30) not null,
    personal_sales_sum real        not null,
    pure_sales_sum     real        not null
);

drop table if exists generation;
create table generation
(
    id               bigserial primary key,
    generation_level int  not null,
    members_qty      int  not null,
    sales_sum        real not null,
    pure_sales_sum   real not null
);

drop table if exists generation_squad;
create table generation_squad
(
    id                 bigserial primary key,
    client_id          bigint references avon.client,
    client_type        varchar(30) not null,
    reward_level       varchar(30) not null,
    personal_sales_sum real        not null,
    pure_sales_sum     real        not null
);