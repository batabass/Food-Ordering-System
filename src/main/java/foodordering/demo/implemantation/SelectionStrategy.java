package foodordering.demo.implemantation;

import foodordering.demo.domain.*;
import java.util.*;;

public interface SelectionStrategy {
    Restaurant selectRestaurant(List<Restaurant> restaurants, Order order);
}