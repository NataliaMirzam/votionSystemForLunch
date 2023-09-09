package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Vote;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote save(int restaurantId, Vote vote, int userId) {
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        vote.setUser(userRepository.findById(userId).orElse(null));
        return voteRepository.save(vote);
    }
}
