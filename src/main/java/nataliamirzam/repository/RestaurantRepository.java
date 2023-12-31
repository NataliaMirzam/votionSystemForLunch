package nataliamirzam.repository;

import nataliamirzam.error.DataConflictException;
import nataliamirzam.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> get(int id);

    default Restaurant getExisted(int id) {
        return get(id).orElseThrow(
                () -> new DataConflictException("Restaurant id=" + id + "   is not exist"));
    }
}
