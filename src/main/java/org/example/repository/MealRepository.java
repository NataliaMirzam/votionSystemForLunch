package org.example.repository;

import org.example.error.DataConflictException;
import org.example.model.Meal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {
    @Query("SELECT m FROM Meal m WHERE m.id = :id and m.restaurant.id=:restaurantId")
    Optional<Meal> get(int id, int restaurantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId")
    List<Meal> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId AND m.date=:dt")
    List<Meal> getAllForDay(int restaurantId, LocalDate dt);

    default Meal getExistedOrBelonged(int restaurantId, int id) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + "   is not exist or doesn't belong to Restaurant id=" + restaurantId));
    }
}
