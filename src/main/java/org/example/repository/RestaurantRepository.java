package org.example.repository;

import org.example.model.Restaurant;
import org.example.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository  extends BaseRepository<Restaurant> {
    @Transactional
    int deleteById(int id);
}
