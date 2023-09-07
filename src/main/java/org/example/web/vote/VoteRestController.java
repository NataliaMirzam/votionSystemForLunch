package org.example.web.vote;

import org.example.model.Meal;
import org.example.model.Vote;
import org.example.service.MealService;
import org.example.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.example.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    static final String REST_URL = "/restaurants";

    @GetMapping("/{restaurant_id}/votes/{vote_id}")
    public Vote get(@PathVariable int restaurant_id, @PathVariable int vote_id) {
        log.info("get vote {} of restaurant {}", vote_id, restaurant_id);
        return service.get(vote_id, restaurant_id);
    }

    @DeleteMapping("/{restaurant_id}/votes/{vote_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurant_id, @PathVariable int vote_id) {
        log.info("delete vote {} of restaurant {}", vote_id, restaurant_id);
        service.delete(vote_id, restaurant_id);
    }

    @GetMapping("/{restaurant_id}/votes")
    public List<Vote> getAll(@PathVariable int restaurant_id) {
        return service.getAll(restaurant_id);
    }

    @PutMapping(value = "/{restaurant_id}/votes/{vote_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int restaurant_id, @PathVariable int vote_id) {
        log.info("update vote {} with id={} of restaurant {} ", vote, vote_id, restaurant_id);
        assureIdConsistent(vote, vote_id);
        service.update(vote, restaurant_id);
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody Vote vote, @PathVariable int restaurant_id) {
        log.info("create vote {}", vote);
        checkNew(vote);
        Vote created = service.create(vote, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
