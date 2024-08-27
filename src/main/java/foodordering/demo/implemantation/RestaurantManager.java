package foodordering.demo.implemantation;

import foodordering.demo.domain.Restaurant;
import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;

import java.util.*;

public interface RestaurantManager {

    public void addRestaurant(Restaurant restaurant);

    public boolean dropRestautant(int restaurantId);

    // public List<Menuitem> getAvailableItems(int restaurantId);

    public boolean canProcessOrder(int restaurantId);

    public void updateRestaurantMenu(int restaurantId, List<Menuitem> menu);

    public List<Menuitem> getRestaurantMenu(int restaurantId);

    public List<Restaurant> getAllRestaurants();

    public void addItemsToSystemItems(List<Menuitem> items);

    public Restaurant getRestaurantDetails(int restaurantId);

    public String[] getSelectionStrategyOptions();

    public int getCurrentSelectionStrategy();

    public void setSelectionStrategy(int strategy);
    




    
    
}
