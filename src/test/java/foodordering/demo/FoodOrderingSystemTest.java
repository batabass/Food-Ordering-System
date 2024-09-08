package foodordering.demo;

import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;
import foodordering.demo.domain.Restaurant;
import foodordering.demo.implemantation.FoodOrderingSystemImpl;
import foodordering.demo.implemantation.Systemcache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FoodOrderingSystemTest {

    @Mock
    private Systemcache cache; 

    @InjectMocks
    private FoodOrderingSystemImpl foodOrderingSystem; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 
    }


    @Test
    void testCreateOrderID() {
        int orderId1 = FoodOrderingSystemImpl.createOrderID();
        int orderId2 = FoodOrderingSystemImpl.createOrderID();

        assertEquals(orderId1 + 1, orderId2);
    }

    @Test
    void testOrderProcessingStateDelivered() {
        
        Order order = mock(Order.class);
        when(order.isDelivered()).thenReturn(true);
        when(cache.getOrder(1)).thenReturn(order);

        
        String result = foodOrderingSystem.orderProcessingState(1);
        assertEquals("Order Delievered", result);

        when(order.isPicked()).thenReturn(true);
        when(order.isDelivered()).thenReturn(false);
        when(cache.getOrder(1)).thenReturn(order);
        result = foodOrderingSystem.orderProcessingState(1);
        assertEquals("Restaurant is Processing your order", result);
    }

    @Test
    void testCanRestaurantFulFilOrder() {
       
        Restaurant restaurant = mock(Restaurant.class);
        Menuitem item1 = new Menuitem();
        item1.setItemName("Pizza");
        item1.setPrice(220.50);
        item1.setPreparationTime(10);
        Menuitem item2 = new Menuitem();
        item2.setItemName("Cheese Pizza");
        item2.setPrice(330.50);
        item2.setPreparationTime(12);
        List<Menuitem> items = Arrays.asList(item1, item2);

        when(restaurant.getMenu()).thenReturn(items);
        when(restaurant.getOrderProcessingCapacity()).thenReturn(10); 
        when(restaurant.getOrders()).thenReturn(new LinkedBlockingQueue<>()); 
        boolean result = foodOrderingSystem.canRestaurantFulFilOrder(items, restaurant);
        assertTrue(result);
    }

    @Test
    void testCanRestaurantNotFulFilOrderItemNotInMenu() {
       
        Restaurant restaurant = mock(Restaurant.class);
        Menuitem item1 = new Menuitem();
        item1.setItemName("Pizza");
        item1.setPrice(220.50);
        item1.setPreparationTime(10);
        List<Menuitem> items = Arrays.asList(item1);

        Menuitem item2 = new Menuitem();
        item2.setItemName("Sushi");
        item2.setPrice(500.50);
        item2.setPreparationTime(20);

        when(restaurant.getMenu()).thenReturn(Arrays.asList(item2)); 
        when(restaurant.getOrderProcessingCapacity()).thenReturn(10); 
        when(restaurant.getOrders()).thenReturn(new LinkedBlockingQueue<>()); 

        
        boolean result = foodOrderingSystem.canRestaurantFulFilOrder(items, restaurant);

        assertFalse(result);
    }

    @Test
    void testPlaceOrderNoRestaurantAvailable() {
        
        Order order = mock(Order.class);
        when(cache.getAllRestaurants()).thenReturn(Map.of()); 

        
        Exception exception = assertThrows(Exception.class, () -> foodOrderingSystem.placeOrder(order));
        assertEquals("Order cannot be currenlty Processed. \n No restaurant available to fulfil order", exception.getMessage());
    }
    
}
