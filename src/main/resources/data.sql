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
), (
  'Guest',
  'guest@gmail.com',
  'guest'
);
insert into user_role (role, user_id)
values (
  'USER', 
  1
), (
  'ADMIN', 
  2
), (
  'USER',
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
insert into vote (user_id, dt, tm, restaurant_id)
values (
  1,
  cast(current_timestamp as date),
  cast(current_timestamp as time),
  1
), (
  2,
  cast(current_timestamp as date),
  cast(current_timestamp as time),
  1
), (
  1,
  cast(current_timestamp-1 as date),
  cast(current_timestamp as time),
  2
), (
  2,
  cast(current_timestamp-1 as date),
  cast(current_timestamp as time),
  2
), (
  1,
  cast(current_timestamp-2 as date),
  cast(current_timestamp as time),
  3
), (
  1,
  cast(current_timestamp-3 as date),
  cast(current_timestamp as time),
  1
), (
  2,
  cast(current_timestamp-3 as date),
  cast(current_timestamp as time),
  3
);