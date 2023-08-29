package org.example.web.restaurants;

import org.example.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/restaurants")
public class UserRestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("restaurants", restaurantService.getAll());
        return "restaurants/user_index";
    }
}
