package org.example.repository.vote;

import org.example.model.Meal;
import org.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurantId AND v.user.id=:userId")
    int deleteByUser(@Param("id") int id, @Param("restaurantId") int restaurantId, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId")
    List<Vote> findAllByRestaurant(@Param("restaurantId") int restaurantId);
}
