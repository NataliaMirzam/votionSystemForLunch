package nataliamirzam.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nataliamirzam.model.Vote;
import nataliamirzam.repository.VoteRepository;
import nataliamirzam.service.VoteService;
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

import static nataliamirzam.util.validation.ValidationUtil.assureIdConsistent;
import static nataliamirzam.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    @Autowired
    private VoteRepository repository;
    private VoteService service;

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{restaurantId}/votes/{voteId}")
    public ResponseEntity<Vote> get(@PathVariable int restaurantId, @PathVariable int voteId) {
        log.info("get vote {} of restaurant {}", voteId, restaurantId);
        return ResponseEntity.of(repository.get(voteId, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/votes/{voteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId,
                       @PathVariable int voteId) {
        int userId = authUser.id();
        log.info("delete vote {} of restaurant {} with user {}", voteId, restaurantId, userId);
        Vote vote = repository.getExistedOrBelonged(userId, voteId);
        repository.delete(voteId, restaurantId, userId);
    }

    @GetMapping("/{restaurantId}/votes")
    public List<Vote> getAll(@PathVariable int restaurantId,
                             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll votes");
        if (date == null) {
            return repository.getAll(restaurantId);
        } else {
            return repository.getAllForDay(restaurantId, date);
        }
    }

    @PutMapping(value = "/{restaurantId}/votes/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody Vote vote, @PathVariable int restaurantId, @PathVariable int voteId) {
        int userId = authUser.id();
        log.info("update vote {} of restaurant {} with user {}", voteId, restaurantId, userId);
        assureIdConsistent(vote, voteId);
        repository.getExistedOrBelonged(userId, voteId);
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            service.save(restaurantId, vote, userId);
        }
    }

    @PostMapping(value = "/{restaurantId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody Vote vote, @PathVariable int restaurantId) {
        log.info("create vote {} of restaurant {}", vote, restaurantId);
        checkNew(vote);
        Vote created = service.save(restaurantId, vote, authUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
