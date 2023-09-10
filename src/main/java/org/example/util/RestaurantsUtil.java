package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.model.Restaurant;
import org.example.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantsUtil {
    public static List<RestaurantTo> createListRestaurantTo(Collection<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> createTo(restaurant)).collect(Collectors.toList());
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}
