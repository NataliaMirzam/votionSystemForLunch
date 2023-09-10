package org.example.repository;

import org.example.error.DataConflictException;
import org.example.model.Vote;
import org.example.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.id = :id and v.restaurant.id=:restaurantId")
    Optional<Vote> get(int id, int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.id = :id and v.user.id=:userId")
    Optional<Vote> getByUser(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId")
    List<Vote> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.date=:dt")
    List<Vote> getAllForDay(int restaurantId, LocalDate dt);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurantId AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId, @Param("userId") int userId);

    default Vote getExistedOrBelonged(int userId, int id) {
        return getByUser(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }
}
