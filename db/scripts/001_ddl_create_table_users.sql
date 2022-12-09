CREATE TABLE if NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

comment on table users is 'Пользователи';
comment on column users.id is 'Идентификатор пользователя';
comment on column users.username is 'Имя пользователя';
comment on column users.email is 'Почта пользователя';
comment on column users.phone is 'Телефонный номер пользователя';


