package org.example.web.restaurants;

import org.example.model.Restaurant;
import org.example.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantRepository repository;

    @Autowired
    public AdminRestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("restaurants", repository.getAll());
        return "restaurants/admin_index";
    }

    @GetMapping("/new")
    public String newRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        return "restaurants/new";
    }
}
