package org.example.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Restaurant;
import org.example.model.Role;
import org.example.repository.RestaurantRepository;
import org.example.web.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.example.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    @Autowired
    private RestaurantRepository repository;

    public static final String REST_URL = "/api/restaurants";

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete restaurant {} with user {}", id, authUser.id());
        if (authUser.hasRole(Role.ADMIN)) {
            Restaurant restaurant = repository.getExisted(id);
            repository.delete(restaurant);
        }
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return repository.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {} with id={} with user {}", restaurant, id, authUser.id());
        if (authUser.hasRole(Role.ADMIN)) {
            assureIdConsistent(restaurant, id);
            repository.getExisted(id);
            repository.save(restaurant);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                         @Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        if (authUser.hasRole(Role.ADMIN)) {
            checkNew(restaurant);
            Restaurant created = repository.save(restaurant);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else
            return null;
    }
}
