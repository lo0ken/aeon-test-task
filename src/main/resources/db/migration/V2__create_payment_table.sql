create table payment(
    id serial primary key,
    user_id int not null references users(id),
    amount numeric not null,
    new_balance numeric not null,
    creation_date timestamp not null
);