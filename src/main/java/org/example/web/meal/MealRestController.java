package org.example.web.meal;

import org.example.model.Meal;
import org.example.service.MealService;
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
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    static final String REST_URL = "/restaurants";

    @GetMapping("/{restaurant_id}/meals/{meal_id}")
    public Meal get(@PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("get meal {} of restaurant {}", meal_id, restaurant_id);
        return service.get(meal_id, restaurant_id);
    }

    @DeleteMapping("/{restaurant_id}/meals/{meal_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("delete meal {} of restaurant {}", meal_id, restaurant_id);
        service.delete(meal_id, restaurant_id);
    }

    @GetMapping("/{restaurant_id}/meals")
    public List<Meal> getAll(@PathVariable int restaurant_id) {
        return service.getAll(restaurant_id);
    }

    @PutMapping(value = "/{restaurant_id}/meals/{meal_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int restaurant_id, @PathVariable int meal_id) {
        log.info("update meal {} with id={} of restaurant {} ", meal, meal_id, restaurant_id);
        assureIdConsistent(meal, meal_id);
        service.update(meal, restaurant_id);
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal, @PathVariable int restaurant_id) {
        log.info("create meal {}", meal);
        checkNew(meal);
        Meal created = service.create(meal, restaurant_id);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
