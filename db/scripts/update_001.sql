CREATE TABLE if NOT EXISTS users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR NOT NULL,
                       email VARCHAR NOT NULL UNIQUE,
                       phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE if NOT EXISTS sessions (
                          id SERIAL PRIMARY KEY,
                          name text
);

CREATE TABLE if NOT EXISTS ticket (
                        id SERIAL PRIMARY KEY,
                        session_id INT NOT NULL REFERENCES sessions(id),
                        pos_row INT NOT NULL,
                        cell INT NOT NULL,
                        user_id INT NOT NULL REFERENCES users(id)
);