package org.example.web.restaurants;

import org.example.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/restaurants")
public class UserRestaurantController {
    private final RestaurantRepository repository;

    @Autowired
    public UserRestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("restaurants", repository.getAll());
        return "restaurants/user_index";
    }
}