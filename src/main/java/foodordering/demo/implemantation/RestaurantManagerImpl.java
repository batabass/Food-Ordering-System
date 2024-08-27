package foodordering.demo.implemantation;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;
import foodordering.demo.domain.Restaurant;
import org.springframework.stereotype.Service;


@Service
public class RestaurantManagerImpl implements RestaurantManager {

    private static int resIdCounter = 0;
    private static int itemIdCounter = 0;

    private final Map<Integer, ExecutorService> restaurantExecutors = new ConcurrentHashMap<>();


    Systemcache cache = Systemcache.getInstance();

    public static synchronized int createRestaurantID()
    {
        return resIdCounter++;
    }

    public static synchronized int createItemID()
    {
        return itemIdCounter++;
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        

        int restaurantId = createRestaurantID();
        restaurant.setRestaurantId(restaurantId);
        cache.addOrUpdateRestaurant(restaurantId, restaurant);
    }

    @Override
    public Restaurant getRestaurantDetails(int restaurantId) {
        
       return  cache.getRestaurant(restaurantId);
    }

    @Override
    public boolean dropRestautant(int restaurantId) {
        return false;
    }

    @Override
    public boolean canProcessOrder(int restaurantId) {
        
        Restaurant restaurant = cache.getRestaurant(restaurantId);
        int totalCapacity = restaurant.getOrderProcessingCapacity();
        int totalOrderCurrentlyProcessing = restaurant.getOrders().size();
        if(totalOrderCurrentlyProcessing< totalCapacity)
            return true;
        return false;

    }
    

    @Override
    public void updateRestaurantMenu(int restaurantId, List<Menuitem> menu)
    {
        List<Menuitem> allItemsInSystem = cache.getAvailableItems();
        List<String> itemNames = allItemsInSystem.stream().map(Menuitem::getItemName).map(String::toLowerCase).collect(Collectors.toList());
        addItemsToSystemItems(menu);
        cache.addItemsToSystem(menu);
        
    }

    @Override
    public void addItemsToSystemItems(List<Menuitem> items)
    {
        cache.addItemsToSystem(items);
    }

    @Override
    public List<Menuitem> getRestaurantMenu(int restaurantId)
    {
        return cache.getRestaurant(restaurantId).getMenu();

    }

    @Override
    public List<Restaurant> getAllRestaurants()
    {
        return new ArrayList<>(cache.getAllRestaurants().values());

    }

    @Override
    public String[] getSelectionStrategyOptions(){
        return SelectionStrategyEnum.getValues();

    }

    @Override
    public int getCurrentSelectionStrategy(){
        return cache.getSelectionStrategy();
    }

    @Override
    public void setSelectionStrategy(int strategy){
        cache.setSelectionStrategy(strategy);

    }
    


    
}
