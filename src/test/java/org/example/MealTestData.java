package org.example;

import org.example.model.Meal;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.example.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "restaurant");
//    public static MatcherFactory.Matcher<MealTo> TO_MATCHER = MatcherFactory.usingEqualsComparator(MealTo.class);

    public static final int NOT_FOUND = 10;
    public static final int MEAL_ID = START_SEQ + 5;

    public static final Meal meal1 = new Meal(MEAL_ID, "Italian breakfast", now().toLocalDate());
    public static final Meal meal2 = new Meal(MEAL_ID + 1, "Italian lunch", now().toLocalDate());
    public static final Meal meal3 = new Meal(MEAL_ID + 2, "Italian dinner", now().toLocalDate());
    public static final Meal meal4 = new Meal(MEAL_ID + 3, "Chinese breakfast", now().toLocalDate());
    public static final Meal meal5 = new Meal(MEAL_ID + 4, "Chinese lunch", now().toLocalDate());
    public static final Meal meal6 = new Meal(MEAL_ID + 5, "Chinese dinner", now().toLocalDate());
    public static final Meal meal7 = new Meal(MEAL_ID + 6, "Hindian breakfast", now().toLocalDate());
    public static final Meal meal8 = new Meal(MEAL_ID + 6, "Hindian lunch", now().toLocalDate());

    public static final List<Meal> listOfMeals1 = List.of(meal1, meal2, meal3);
    public static final List<Meal> listOfMeals2 = List.of(meal4, meal5, meal6);
    public static final List<Meal> listOfMeals3 = List.of(meal7, meal8);

//    public static Meal getNew() {
//        return new Meal(null, of(2020, Month.FEBRUARY, 1, 18, 0), "Созданный ужин", 300);
//    }
//
//    public static Meal getUpdated() {
//        return new Meal(MEAL_ID, meal1.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак", 200);
//    }
}
