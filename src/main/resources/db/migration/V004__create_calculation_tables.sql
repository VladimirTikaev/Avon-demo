drop table if exists avon.sales_percent;
create table avon.sales_percent
(
    id                 bigserial primary key,
    total_sales        bigint not null,
    first_gen_percent  real   not null,
    second_gen_percent real   not null
);

drop table if exists avon.remuneration_level_calc;
create table avon.remuneration_level_calc
(
    id                 bigserial primary key,
    total_sales        bigint      not null,
    remuneration_level varchar(30) not null
)