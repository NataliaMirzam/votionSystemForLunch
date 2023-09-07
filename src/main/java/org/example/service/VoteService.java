package org.example.service;

import org.example.model.*;
import org.example.repository.meal.MealRepository;
import org.example.repository.restaurant.RestaurantRepository;
import org.example.repository.user.UserRepository;
import org.example.repository.vote.VoteRepository;
import org.example.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.example.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("voteService")
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote create(Vote vote, int restaurant_id) {
        Assert.notNull(vote, "vote must not be null");
        return voteRepository.save(vote, restaurant_id, getUserId());
    }

    public void delete(int vote_id, int restaurant_id) {
        checkModificationAllowed(vote_id);
        checkNotFoundWithId(voteRepository.delete(vote_id, restaurant_id, getUserId()), vote_id);
    }

    public Vote get(int vote_id, int restaurant_id) {
        return checkNotFoundWithId(voteRepository.get(vote_id, restaurant_id), vote_id);
    }

    public List<Vote> getAll(int restaurant_id) {
        return voteRepository.getAll(restaurant_id);
    }

    public void update(Vote vote, int restaurant_id) {
        Assert.notNull(vote, "vote must not be null");
        checkModificationAllowed(vote.id());
        voteRepository.save(vote, restaurant_id, getUserId());
    }

    protected void checkModificationAllowed(int id) {
        if (id < AbstractBaseEntity.START_SEQ + 2) {
            throw new RuntimeException();
        }
    }

    protected int getUserId() {
        return SecurityUtil.authUserId();
    }
}
