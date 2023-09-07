package org.example.repository.meal;

import org.example.model.Meal;
import org.example.repository.restaurant.CrudRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository{
    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Meal save(Meal meal, int restaurantId) {
        if (!meal.isNew() && (get(meal.id(), restaurantId) == null)) {
            return null;
        }
        meal.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMealRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Meal get(int id, int restaurantId) {
        return crudMealRepository.findById(id)
                .filter(meal -> meal.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return crudMealRepository.findAllByRestaurant(restaurantId);
    }
}
