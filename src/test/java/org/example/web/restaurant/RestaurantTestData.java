package org.example.web.restaurant;

import org.example.model.Restaurant;
import org.example.to.RestaurantTo;
import org.example.web.MatcherFactory;

import java.util.List;

import static org.example.web.meal.MealTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "meals");
    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT_ID = 1;
    public static final int RESTAURANT_ID_INCORRECT = 10;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Italian");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "Chinese");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID + 2, "Hindian");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    static {
        restaurant1.setMeals(listOfMeals1);
        restaurant2.setMeals(listOfMeals2);
        restaurant3.setMeals(listOfMeals3);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Созданный ресторан");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Обновленный ресторан");
    }
}
