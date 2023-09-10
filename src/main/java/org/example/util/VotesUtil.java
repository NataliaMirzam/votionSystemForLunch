package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.model.Vote;
import org.example.to.VoteTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VotesUtil {
    public static List<VoteTo> createListVoteTo(Collection<Vote> votes) {
        return votes.stream().map(vote -> createTo(vote)).collect(Collectors.toList());
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate());
    }
}
