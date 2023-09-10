delete from user_role;
delete from users;
delete from meal;
delete from restaurant;
insert into users (name, email, password)
values (
  'User', 
  'user@yandex.ru', 
  'password'
), (
  'Admin', 
  'admin@gmail.com', 
  'admin'
);
insert into user_role (role, user_id)
values (
  'USER', 
  1
), (
  'ADMIN', 
  2
);
insert into restaurant (name)
values ('Italian'), ('Chinese'), ('Hindian');
insert into meal (name, restaurant_id)
values (
  'Italian breakfast', 
  1
), (
  'Italian lunch', 
  1
), (
  'Italian dinner', 
  1
), (
  'Chinese breakfast', 
  2
), (
  'Chinese lunch', 
  2
), (
  'Chinese dinner', 
  2
), (
  'Hindian breakfast', 
  3
), (
  'Hindian lunch',
  3
);