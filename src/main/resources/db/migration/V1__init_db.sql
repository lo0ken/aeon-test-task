create table users (
    id serial primary key,
    login varchar unique not null,
    password varchar not null,
    balance numeric not null
);

insert into users (login, password, balance)
values ('admin1', '$2y$12$a.OQYWlmbUEV1Kf3PLePQeHppJ9jOopd53l7nu1D2IBdASKG2NPQa', 8);