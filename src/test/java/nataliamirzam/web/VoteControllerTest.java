package nataliamirzam.web;

import nataliamirzam.util.JsonUtil;
import nataliamirzam.util.VotesUtil;
import nataliamirzam.web.user.UserTestData;
import nataliamirzam.model.Vote;
import nataliamirzam.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static nataliamirzam.web.RestaurantTestData.RESTAURANT_ID;
import static nataliamirzam.web.VoteController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.vote1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.VOTE_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.VOTE_ID))
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.get(VoteTestData.VOTE_ID, RESTAURANT_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void deleteDataConflict() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.NOT_FOUND))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT_ID + "/votes/" + VoteTestData.VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        VoteTestData.VOTE_MATCHER.assertMatch(voteRepository.getExisted(VoteTestData.VOTE_ID), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RESTAURANT_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VoteTestData.VOTE_MATCHER.assertMatch(created, newVote);
        VoteTestData.VOTE_MATCHER.assertMatch(voteRepository.getExisted(newId), newVote);
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID + "/votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VotesUtil.createListVoteTo(VoteTestData.listOfVotes1)));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllByDay() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_ID + "/votes")
                .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VotesUtil.createListVoteTo(VoteTestData.listOfVotes2)));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createInvalid() throws Exception {
        Vote invalid = new Vote(null, null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + RESTAURANT_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}