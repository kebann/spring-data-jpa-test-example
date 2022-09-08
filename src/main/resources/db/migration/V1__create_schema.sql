CREATE TABLE authors
(
    id         BIGSERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL
);

CREATE TABLE notes
(
    id         BIGSERIAL PRIMARY KEY,
    body       TEXT      NOT NULL,
    title      TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL default current_date,
    updated_at TIMESTAMP NOT NULL default current_date,
    author_id  BIGSERIAL not null REFERENCES authors (id)
);
