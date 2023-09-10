package org.example.web.meal;

import org.example.model.Meal;
import org.example.repository.MealRepository;
import org.example.service.MealService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.MealTestData.MEAL_ID;
import static org.example.RestaurantTestData.RESTAURANT_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MealRestControllerTest {

    @Autowired
    protected MealService service;
    protected MealRepository repository;

    @Test
    void delete() {
//        Meal meal = repository.getExistedOrBelonged(RESTAURANT_ID, MEAL_ID);
//        repository.delete(meal);
//        assertThrows(RuntimeException.class, () -> repository.get(MEAL_ID, RESTAURANT_ID));
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}