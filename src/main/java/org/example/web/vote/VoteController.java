package org.example.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Meal;
import org.example.model.Restaurant;
import org.example.model.Vote;
import org.example.repository.VoteRepository;
import org.example.service.VoteService;
import org.example.web.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.example.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    @Autowired
    private VoteRepository repository;
    private VoteService service;

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{restaurant_id}/votes/{vote_id}")
    public ResponseEntity<Vote> get(@PathVariable int restaurant_id, @PathVariable int vote_id) {
        log.info("get vote {} of restaurant {}", vote_id, restaurant_id);
        return ResponseEntity.of(repository.get(vote_id, restaurant_id));
    }

    @DeleteMapping("/{restaurant_id}/votes/{vote_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurant_id,
                       @PathVariable int vote_id) {
        int user_id = authUser.id();
        log.info("delete vote {} of restaurant {} with user {}", vote_id, restaurant_id, user_id);
        Vote vote = repository.getExistedOrBelonged(user_id, vote_id);
        repository.delete(vote_id, restaurant_id, user_id);
    }

    @GetMapping("/{restaurant_id}/votes")
    public List<Vote> getAll(@PathVariable int restaurant_id,
                             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll votes");
        if (date == null) {
            return repository.getAll(restaurant_id);
        } else {
            return repository.getAllForDay(restaurant_id, date);
        }
    }

    @PutMapping(value = "/{restaurant_id}/votes/{vote_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody Vote vote, @PathVariable int restaurant_id, @PathVariable int vote_id) {
        int user_id = authUser.id();
        log.info("update vote {} of restaurant {} with user {}", vote_id, restaurant_id, user_id);
        assureIdConsistent(vote, vote_id);
        repository.getExistedOrBelonged(user_id, vote_id);
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            service.save(restaurant_id, vote, user_id);
        }
    }

    @PostMapping(value = "/{restaurant_id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody Vote vote, @PathVariable int restaurant_id) {
        log.info("create vote {} of restaurant {}", vote, restaurant_id);
        checkNew(vote);
        Vote created = service.save(restaurant_id, vote, authUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
