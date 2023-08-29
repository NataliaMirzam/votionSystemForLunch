package org.example.web.restaurants;

import org.example.model.Restaurant;
import org.example.model.User;
import org.example.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public AdminRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("restaurants", restaurantService.getAll());
        return "restaurants/admin_index";
    }

    @GetMapping("/new")
    public String newRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        return "restaurants/new";
    }
}
