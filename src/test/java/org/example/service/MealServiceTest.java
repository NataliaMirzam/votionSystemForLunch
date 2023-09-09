package org.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:db/initDB.sql", "classpath:db/populateDB.sql"})
class MealServiceTest {

    @Autowired
    protected MealService service;

    @Test
    void create() {
    }

    @Test
    void delete() {
//        service.delete(MEAL_ID, RESTAURANT_ID);
//        assertThrows(RuntimeException.class, () -> service.get(MEAL_ID, RESTAURANT_ID));
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }


}