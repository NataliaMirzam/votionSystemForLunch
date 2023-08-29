package org.example.service;

import org.example.model.Restaurant;
import org.example.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    public Restaurant get(int id) {
        return repository.get(id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }
}
