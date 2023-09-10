DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meal;
DELETE FROM restaurant;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);

INSERT INTO restaurant (name)
VALUES ('Italian'),
       ('Chinese'),
       ('Hindian');

INSERT INTO meal (name, restaurant_id)
VALUES ('Italian breakfast', 1),
       ('Italian lunch', 1),
       ('Italian dinner', 1),
       ('Chinese breakfast', 2),
       ('Chinese lunch', 2),
       ('Chinese dinner', 2),
       ('Hindian breakfast', 3),
       ('Hindian lunch', 3);
