package foodordering.demo.implemantation;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;
import foodordering.demo.domain.Restaurant;

import org.springframework.stereotype.Service;

@Service
public class FoodOrderingSystemImpl implements FoodOrderingSystem {

    
    Systemcache cache = Systemcache.getInstance();

    private static int orderIdCounter = 0;

    public static synchronized int createOrderID()
    {
        return orderIdCounter++;
    }

    @Override
    public String orderProcessingState(int orderId) {
        boolean isPicked = cache.getOrder(orderId).isPicked();
        boolean isDelivered = cache.getOrder(orderId).isDelivered();
        if(isDelivered)
            return "Order Delievered";
        else if(isPicked)
            return "Restaurant is Processing your order";

        return "Order will be processing shortly";
    }

    public boolean canRestaurantFulFilOrder(List<Menuitem> items, Restaurant restaurant)
    {
        List<String> restaurantMenu = restaurant.getMenu().stream().map(Menuitem::getItemName).map(String::toLowerCase).collect(Collectors.toList());
        
        for(Menuitem item:items)
        {
            String name = item.getItemName().toLowerCase();
            int capacity = restaurant.getOrderProcessingCapacity();
            int orderQueueSize = restaurant.getOrders().size();
            if(!restaurantMenu.contains(name) || orderQueueSize == capacity)
                return false;
        }
        return true;
    }

    private int canOrderBeProcessedByOneRestaurant(Order order)
    {
        List<Menuitem> items = order.getOrderItems();
        Map<Integer,Restaurant> allRestaurant = cache.getAllRestaurants();
        List<Restaurant> restaurantsFufilingOrder = new ArrayList<>();
        for(Restaurant restaurant:allRestaurant.values())
        {
            System.out.println("Checking for:"+restaurant.getName());
            if(canRestaurantFulFilOrder(items, restaurant))
            {
                restaurantsFufilingOrder.add(restaurant);
            }
        }
        for(Restaurant restaurant:restaurantsFufilingOrder )
        {
            System.out.println(restaurant.getName()+" can fulfil order");
        }
        if(restaurantsFufilingOrder.size()>0)
        {
            Restaurant res = null;
            if(getRestaurantSelectionStrategy() == 0)
            {
                res = new TotalOrderPriceStrategy().selectRestaurant(restaurantsFufilingOrder,order);
                
            }
            else
            {
                res = new RestaurantRatingStrategy().selectRestaurant(restaurantsFufilingOrder,order);
            }
            return res.getRestaurantId();
        }
        
        return -1;
    }

    @Override
    public int placeOrder(Order order) throws Exception {
        int restaurantId = canOrderBeProcessedByOneRestaurant(order);
        if(restaurantId == -1)
            throw new Exception("Order cannot be currenlty Processed. \n No restaurant available to fulfil order");
        int orderId = createOrderID();
        order.setOrderId(orderId);
        Restaurant restaurant = cache.getRestaurant(restaurantId);
        restaurant.getOrders().add(order);
        cache.addOrUpdateRestaurant(restaurantId, restaurant);
        cache.addOrUpdateOrder(orderId, order);
        new Thread(() -> processOrder(restaurant, order)).start();

        return orderId;
    }

    private int getRestaurantSelectionStrategy()
    {
        return cache.getSelectionStrategy();
    }

    private void processOrder(Restaurant restaurant, Order order) {
        try {

            int orderId = order.getOrderId();
            order.setPicked(true);
            cache.addOrUpdateOrder(orderId, order);
            List<Menuitem> menuitems = restaurant.getMenu();
            Map<String,Integer> itemToPrepTimeMap = menuitems.stream().collect(Collectors.toMap(item -> item.getItemName().toLowerCase(), Menuitem::getPreparationTime));
            int totalTime = 0;
            for(Menuitem item:order.getOrderItems())
            {
                String name = item.getItemName().toLowerCase();
                totalTime+=itemToPrepTimeMap.get(name);

            }
            //Preparing Order
            Thread.sleep(totalTime*1000); 
          
            restaurant.getOrders().remove(order);
            
            order.setDelivered(true);
            cache.addOrUpdateOrder(order.getOrderId(), order);
            System.out.println("Order " + order.getOrderId() + " processed successfully.");
    
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Order processing interrupted for order " + order.getOrderId());
        }
    }

    @Override
    public void setRestaurantSelectionStrategy(boolean byRating, boolean byPrice) {
        
    }

    @Override
    public List<Menuitem> showAvailableItems() {
        
        return cache.getAvailableItems();
    }

    
    
}
