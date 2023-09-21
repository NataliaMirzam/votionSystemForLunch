package nataliamirzam.util;

import lombok.experimental.UtilityClass;
import nataliamirzam.to.VoteTo;
import nataliamirzam.model.Vote;

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
