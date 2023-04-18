drop table if exists avon.sales_percent;
create table avon.sales_percent
(
    id                 bigserial primary key,
    total_sales        bigint not null,
    first_gen_percent  real   not null,
    second_gen_percent real   not null
);