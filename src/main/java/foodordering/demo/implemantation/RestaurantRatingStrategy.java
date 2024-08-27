package foodordering.demo.implemantation;


import java.util.*;
import java.util.stream.Collectors;
import foodordering.demo.domain.*;

public class RestaurantRatingStrategy implements SelectionStrategy {
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, Order order) {

        Restaurant res = null;
        double maxRating = Integer.MIN_VALUE;
        for(Restaurant restaurant:restaurants )
        {
            if(restaurant.getRating()> maxRating)
            {
                res= restaurant;
                maxRating = restaurant.getRating();
            }
        }
        return res;
        
    }
}