package nataliamirzam.util;

import lombok.experimental.UtilityClass;
import nataliamirzam.model.Meal;
import nataliamirzam.to.MealTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@UtilityClass
public class MealsUtil {

    public static List<MealTo> createListMealTo(Collection<Meal> meals) {
        return meals.stream().map(meal -> createTo(meal)).collect(Collectors.toList());
    }

    public static MealTo createTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.getDate());
    }
}
