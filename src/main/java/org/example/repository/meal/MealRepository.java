package org.example.repository.meal;

import org.example.model.Meal;
import org.example.model.Restaurant;

import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to restaurant_id
    Meal save(Meal meal, int restaurant_id);

    // false if meal does not belong to restaurant_id
    boolean delete(int id, int restaurant_id);

    // null if meal does not belong to restaurant_id
    Meal get(int id, int restaurant_id);

    List<Meal> getAll(int restaurant_id);
}
