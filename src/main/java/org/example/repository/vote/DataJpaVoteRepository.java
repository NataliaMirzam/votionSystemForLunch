package org.example.repository.vote;

import org.example.model.Vote;
import org.example.repository.restaurant.CrudRestaurantRepository;
import org.example.repository.user.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static java.time.LocalTime.now;

@Repository
public class DataJpaVoteRepository implements VoteRepository{
    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository,
                                 CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int restaurantId, int userId) {
        LocalTime elevenOClock = LocalTime.of(7, 0);

        if ((!vote.isNew() && (get(vote.id(), restaurantId, userId) == null)) ||
            (!vote.isNew() && (now().isAfter(elevenOClock)))) {
            return null;
        }
        vote.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        vote.setUser(crudUserRepository.getReferenceById(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int restaurantId, int userId) {
        return crudVoteRepository.deleteByUser(id, restaurantId, userId) != 0;
    }

    @Override
    public Vote get(int id, int restaurantId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public Vote get(int id, int restaurantId, int userId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getRestaurant().getId() == restaurantId && vote.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return crudVoteRepository.findAllByRestaurant(restaurantId);
    }
}
