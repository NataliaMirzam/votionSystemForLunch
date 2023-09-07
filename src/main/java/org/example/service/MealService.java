package org.example.service;

import org.example.model.AbstractBaseEntity;
import org.example.model.Meal;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.meal.MealRepository;
import org.example.repository.user.UserRepository;
import org.example.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.example.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("mealService")
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public Meal create(Meal meal, int restaurant_id) {
        Assert.notNull(meal, "meal must not be null");
        checkAdmin();
        return mealRepository.save(meal, restaurant_id);
    }

    public void delete(int meal_id, int restaurant_id) {
        checkModificationAllowed(meal_id);
        checkAdmin();
        checkNotFoundWithId(mealRepository.delete(meal_id, restaurant_id), meal_id);
    }

    public Meal get(int meal_id, int restaurant_id) {
        return checkNotFoundWithId(mealRepository.get(meal_id, restaurant_id), meal_id);
    }

    public List<Meal> getAll(int restaurant_id) {
        return mealRepository.getAll(restaurant_id);
    }

    public void update(Meal meal, int restaurant_id) {
        Assert.notNull(meal, "meal must not be null");
        checkModificationAllowed(meal.id());
        checkAdmin();
        mealRepository.save(meal, restaurant_id);
    }

    protected void checkModificationAllowed(int id) {
        if (id < AbstractBaseEntity.START_SEQ + 2) {
            throw new RuntimeException();
        }
    }

    protected void checkAdmin() {
        int authUserId = SecurityUtil.authUserId();
        User user = userRepository.get(authUserId);
        if (!user.getRoles().contains(Role.ADMIN)) {
            throw new RuntimeException("User must have administration rights");
        }
    }
}
