package foodordering.demo.implemantation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import foodordering.demo.domain.Restaurant;
import foodordering.demo.domain.Order;
import foodordering.demo.domain.Menuitem;
import java.util.concurrent.atomic.AtomicInteger;



public class Systemcache {

    

    private Systemcache() {}

    private static Systemcache instance = null;

    public static synchronized Systemcache getInstance() {
        if (instance == null) {
            instance = new Systemcache();
        }
        return instance;
    }

    private final Map<Integer, Restaurant> restaurantCache = new ConcurrentHashMap<>();
    private final Map<Integer, Order> orderCache = new ConcurrentHashMap<>();
    private final List<Menuitem> availableItems = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger selectionStrategy = new AtomicInteger(0); 


    public int getSelectionStrategy() {
        return selectionStrategy.get();
    }

    public void setSelectionStrategy(int strategy) {
        selectionStrategy.set(strategy);
    }

    public void addOrUpdateRestaurant(int restaurantId, Restaurant restaurantData) {
        restaurantCache.put(restaurantId, restaurantData);
    }

    public Restaurant getRestaurant(int restaurantId) {
        return restaurantCache.get(restaurantId);
    }

    public void addOrUpdateOrder(int orderId, Order orderData) {
        orderCache.put(orderId, orderData);
    }

    public Order getOrder(int orderId) {
        return orderCache.get(orderId);
    }

    public void addItemsToSystem(List<Menuitem> items) {

        List<String> itemNames = availableItems.stream().map(Menuitem::getItemName).map(String::toLowerCase).collect(Collectors.toList());
        for(Menuitem item:items)
        {
            
            if(!itemNames.contains(item.getItemName().toLowerCase()))
            {
                availableItems.add(item);
            }
            
        }
       
    }

    public List<Menuitem> getAvailableItems() {
        return availableItems;
    }


    public Map<Integer, Order> getAllOrders()
    {
        return orderCache;
    }

    public Map<Integer, Restaurant> getAllRestaurants()
    {
        return restaurantCache;
    }
    
}
