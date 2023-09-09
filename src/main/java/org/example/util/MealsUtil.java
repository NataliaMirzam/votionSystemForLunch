package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.model.Meal;
import org.example.to.MealTo;


@UtilityClass
public class MealsUtil {
    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getName(), meal.getDate());
    }
}
