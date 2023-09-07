package org.example.repository.vote;

import org.example.model.Vote;

import java.util.List;

public interface VoteRepository {
    // null if not found, when updated
    Vote save(Vote vote, int restaurantId, int userId);

    // false if not found
    boolean delete(int id, int restaurantId, int userId);

    // null if not found
    Vote get(int id, int restaurantId);

    // null if not found
    Vote get(int id, int restaurantId, int userId);

    List<Vote> getAll(int restaurantId);
}
