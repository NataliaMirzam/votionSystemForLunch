package org.example;

import org.example.model.Restaurant;

import static org.example.MealTestData.*;

public class RestaurantTestData {
    public static final int RESTAURANT_ID = 1;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Italian");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "Chinese");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID + 2, "Hindian");

    static {
        restaurant1.setMeals(listOfMeals1);
        restaurant2.setMeals(listOfMeals2);
        restaurant3.setMeals(listOfMeals3);
    }
}
