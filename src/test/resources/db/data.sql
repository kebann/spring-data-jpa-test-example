INSERT INTO authors(id, first_name, last_name)
VALUES (1, 'Mike', 'Spike');

INSERT INTO notes(body, title, created_at, updated_at, author_id)
VALUES ('my note 1', 'title 1', current_date + 1, current_date + 1, 1),
       ('my note 2', 'title 2', current_date - 1, current_date - 1, 1),
       ('my note 3', 'title 3', current_date - 2, current_date - 2, 1),
       ('my note 7', 'title 7', current_date, current_date, 1);