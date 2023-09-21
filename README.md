Votion System For Lunch
===============================

Разработка веб REST приложения голосования за рестораны с авторизацией 
и правами доступа на основе ролей на стеке Maven/Spring MVC/Hibernate

- 2 типа пользователей: админы и пользователи
- Админы могут создавать рестораны и их меню на день (просто названия 
и цены)
- Меню меняется каждый день (админы вносят изменения)
- Пользователи могут голосовать за ресторан, в котором они хотят есть сегодня
- Принимается только один голос от пользователя в день
- Если пользователь голосует снова в тот же день:
  - Если до 11:00 - изменения принимаются
  - Если после 11:00 - слишком поздно, голос не может быть изменен

Каждый ресторан предоставляет новое меню каждый день

### Swagger
http://localhost:8080/swagger-ui/index.html