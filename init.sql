create table users
(
    id bigserial
        constraint users_pk
            primary key,
    first_name varchar(255) not null,
    patronymic varchar(255),
    last_name varchar(255) not null
);

