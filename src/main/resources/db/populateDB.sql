DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meal;
DELETE FROM restaurant;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurant (name)
VALUES ('Italian'),
       ('Chinese'),
       ('Hindian');

INSERT INTO meal (name, restaurant_id)
VALUES ('Italian breakfast', 100002),
       ('Italian lunch', 100002),
       ('Italian dinner', 100002),
       ('Chinese breakfast', 100003),
       ('Chinese lunch', 100003),
       ('Chinese dinner', 100003),
       ('Hindian breakfast', 100004),
       ('Hindian lunch', 100004);
