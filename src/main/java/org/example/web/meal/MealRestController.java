package org.example.web.meal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Meal;
import org.example.model.Role;
import org.example.repository.MealRepository;
import org.example.service.MealService;
import org.example.web.AuthUser;
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
import java.util.List;

import static org.example.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MealRestController {
    protected MealRepository repository;
    private final MealService service;

    static final String REST_URL = "/restaurants";

    @GetMapping("/{restaurant_id}/meals/{meal_id}")
    public ResponseEntity<Meal> get(@PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("get meal {} of restaurant {}", meal_id, restaurant_id);
        return ResponseEntity.of(repository.get(meal_id, restaurant_id));
    }

    @DeleteMapping("/{restaurant_id}/meals/{meal_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurant_id,
                       @PathVariable int meal_id) {
        log.info("delete meal {} of restaurant {} with user {}", meal_id, restaurant_id, authUser.id());
        if (authUser.hasRole(Role.ADMIN)) {
            Meal meal = repository.getExistedOrBelonged(restaurant_id, meal_id);
            repository.delete(meal);
        }
    }

    @GetMapping("/{restaurant_id}/meals")
    public List<Meal> getAll(@PathVariable int restaurant_id,
                             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll meals for restaurant {}", restaurant_id);
        if (date == null) {
            return repository.getAll(restaurant_id);
        } else {
            return repository.getAllForDay(restaurant_id, date);
        }
    }

    @PutMapping(value = "/{restaurant_id}/meals/{meal_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Meal meal,
                       @PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("update meal {} of restaurant {} ", meal_id, restaurant_id);
        if (authUser.hasRole(Role.ADMIN)) {
            assureIdConsistent(meal, meal_id);
            repository.getExistedOrBelonged(restaurant_id, meal_id);
            service.save(restaurant_id, meal);
        }
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody Meal meal, @PathVariable int restaurant_id) {
        log.info("create meal {} of restaurant {} ", meal, restaurant_id);
        if (authUser.hasRole(Role.ADMIN)) {
            checkNew(meal);
            Meal created = service.save(restaurant_id, meal);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else
            return null;
    }
}
