package nataliamirzam.service;

import lombok.AllArgsConstructor;
import nataliamirzam.repository.RestaurantRepository;
import nataliamirzam.repository.UserRepository;
import nataliamirzam.model.Vote;
import nataliamirzam.repository.VoteRepository;
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
