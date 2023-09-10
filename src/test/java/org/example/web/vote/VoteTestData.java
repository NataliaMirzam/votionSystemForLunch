package org.example.web.vote;

import org.example.model.Vote;
import org.example.to.VoteTo;
import org.example.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.example.web.restaurant.RestaurantTestData.*;
import static org.example.web.user.UserTestData.admin;
import static org.example.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant", "date", "time");
    public static MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int NOT_FOUND = 10;
    public static final int VOTE_ID = 1;

    public static final Vote vote1 = new Vote(VOTE_ID, user, LocalDate.now(), LocalTime.now(), restaurant1);
    public static final Vote vote2 = new Vote(VOTE_ID + 1, admin, LocalDate.now(), LocalTime.now(), restaurant1);
    public static final Vote vote3 = new Vote(VOTE_ID + 2, user, LocalDate.now().minusDays(1),
            LocalTime.now(), restaurant2);
    public static final Vote vote4 = new Vote(VOTE_ID + 3, admin, LocalDate.now().minusDays(1),
            LocalTime.now(), restaurant2);
    public static final Vote vote5 = new Vote(VOTE_ID + 4, user, LocalDate.now().minusDays(2),
            LocalTime.now(), restaurant3);
    public static final Vote vote6 = new Vote(VOTE_ID + 5, user, LocalDate.now().minusDays(3),
            LocalTime.now(), restaurant1);
    public static final Vote vote7 = new Vote(VOTE_ID + 6, admin, LocalDate.now().minusDays(3),
            LocalTime.now(), restaurant3);

    public static final List<Vote> listOfVotes1 = List.of(vote1, vote2, vote6);
    public static final List<Vote> listOfVotes2 = List.of(vote1, vote2);


    public static Vote getNew() {
        return new Vote(null, null, LocalDate.now().minusDays(4), LocalTime.now(), null);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, null, vote7.getDate(), vote7.getTime(), null);
    }
}
