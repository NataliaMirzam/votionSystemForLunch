package nataliamirzam.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nataliamirzam.model.Meal;
import nataliamirzam.model.Role;
import nataliamirzam.repository.MealRepository;
import nataliamirzam.service.MealService;
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

import static nataliamirzam.util.validation.ValidationUtil.assureIdConsistent;
import static nataliamirzam.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MealController {
    protected MealRepository repository;
    private final MealService service;

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{restaurantId}/meals/{mealId}")
    public ResponseEntity<Meal> get(@PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("get meal {} of restaurant {}", mealId, restaurantId);
        return ResponseEntity.of(repository.get(mealId, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/meals/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId,
                       @PathVariable int mealId) {
        log.info("delete meal {} of restaurant {} with user {}", mealId, restaurantId, authUser.id());
        if (authUser.hasRole(Role.ADMIN)) {
            Meal meal = repository.getExistedOrBelonged(restaurantId, mealId);
            repository.delete(meal);
        }
    }

    @GetMapping("/{restaurantId}/meals")
    public List<Meal> getAll(@PathVariable int restaurantId,
                             @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll meals for restaurant {}", restaurantId);
        if (date == null) {
            return repository.getAll(restaurantId);
        } else {
            return repository.getAllForDay(restaurantId, date);
        }
    }

    @PutMapping(value = "/{restaurantId}/meals/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Meal meal,
                       @PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("update meal {} of restaurant {} ", mealId, restaurantId);
        if (authUser.hasRole(Role.ADMIN)) {
            assureIdConsistent(meal, mealId);
            repository.getExistedOrBelonged(restaurantId, mealId);
            service.save(restaurantId, meal);
        }
    }

    @PostMapping(value = "/{restaurantId}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody Meal meal, @PathVariable int restaurantId) {
        log.info("create meal {} of restaurant {} ", meal, restaurantId);
        if (authUser.hasRole(Role.ADMIN)) {
            checkNew(meal);
            Meal created = service.save(restaurantId, meal);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        } else
            return null;
    }
}
