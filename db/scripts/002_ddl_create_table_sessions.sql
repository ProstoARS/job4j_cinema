CREATE TABLE if NOT EXISTS sessions
(
    id    SERIAL PRIMARY KEY,
    name  text,
    photo bytea
);

comment on table sessions is 'Таблица сеансов кинотеатра';
comment on column sessions.id is 'Идентификатор сеанса';
comment on column sessions.name is 'Название фильма';
comment on column sessions.photo is 'Афиша фильма';