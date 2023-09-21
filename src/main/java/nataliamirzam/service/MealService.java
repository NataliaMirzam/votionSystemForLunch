package nataliamirzam.service;

import lombok.AllArgsConstructor;
import nataliamirzam.model.Meal;
import nataliamirzam.repository.MealRepository;
import nataliamirzam.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Meal save(int restaurantId, Meal meal) {
        meal.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return mealRepository.save(meal);
    }
}
