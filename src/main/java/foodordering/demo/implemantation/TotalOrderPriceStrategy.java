package foodordering.demo.implemantation;


import foodordering.demo.domain.*;
import java.util.*;
import java.util.stream.Collectors;;
public class TotalOrderPriceStrategy implements SelectionStrategy {
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurants, Order order) {
        double minPrice = Integer.MAX_VALUE;
        Restaurant res = null;
        for(Restaurant restaurant:restaurants)
        {   double restaurantPrice = 0.0;
            List<Menuitem> menuItems = restaurant.getMenu();
            Map<String,Double> ItemToPriceMap = menuItems.stream().collect(Collectors.toMap(item -> item.getItemName().toLowerCase(), Menuitem::getPrice));
            for(Menuitem item:order.getOrderItems())
            {
                String name = item.getItemName().toLowerCase();
                restaurantPrice+=ItemToPriceMap.get(name);

            }
            if(restaurantPrice<minPrice)
            {
                res = restaurant;
                minPrice = restaurantPrice;
                System.out.println("Total Order price for "+res.getName()+" ="+restaurantPrice);
            }
        }
        System.out.println("Chosen "+res.getName());
        return res;
    }
}
