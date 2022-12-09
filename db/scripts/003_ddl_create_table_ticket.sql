CREATE TABLE if NOT EXISTS ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions (id),
    pos_row    INT NOT NULL,
    cell       INT NOT NULL,
    user_id    INT NOT NULL REFERENCES users (id),
    UNIQUE (session_id, pos_row, cell)
);

comment on table ticket is 'Билеты';
comment on column ticket.id is 'Идентификатор билета';
comment on column ticket.session_id is 'Привязка к идентификатору сеанса';
comment on column ticket.pos_row is 'Значение ряда зала';
comment on column ticket.cell is 'значение места в ряду зала';
