package org.example.service;

import org.example.model.AbstractBaseEntity;
import org.example.model.Restaurant;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.restaurant.RestaurantRepository;
import org.example.repository.user.UserRepository;
import org.example.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.example.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkAdmin();
        return restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkModificationAllowed(id);
        checkAdmin();
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkModificationAllowed(restaurant.id());
        checkAdmin();
        restaurantRepository.save(restaurant);
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
