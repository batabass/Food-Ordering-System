package foodordering.demo;

import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;
import foodordering.demo.domain.Restaurant;
import foodordering.demo.implemantation.RestaurantManagerImpl;
import foodordering.demo.implemantation.Systemcache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RestaurantManagerTest {

    @Mock
    private Systemcache cache; 

    @InjectMocks
    private RestaurantManagerImpl restaurantManager; 

    private LinkedBlockingQueue<Object> mockOrderQueue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 

        mockOrderQueue = new LinkedBlockingQueue<>();
        mockOrderQueue.add("Order1");
        mockOrderQueue.add("Order2");
        mockOrderQueue.add("Order3");
        mockOrderQueue.add("Order4");
        mockOrderQueue.add("Order5");
    }

    @Test
    void testAddRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        restaurantManager.addRestaurant(restaurant);

        verify(cache, times(1)).addOrUpdateRestaurant(anyInt(), eq(restaurant)); 
        assertEquals(0, restaurant.getRestaurantId()); 
    }

    @Test
    void testGetRestaurantDetails() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        when(cache.getRestaurant(1)).thenReturn(restaurant); 

        Restaurant result = restaurantManager.getRestaurantDetails(1);

        assertNotNull(result);
        assertEquals(1, result.getRestaurantId()); 
    }

    @Test
    void testCanProcessOrder_CanProcess() {
        Restaurant restaurant = new Restaurant();
        restaurant.setOrderProcessingCapacity(5);
        // restaurant.setOrders(Collections.emptyList()); 

        when(cache.getRestaurant(1)).thenReturn(restaurant); 

        boolean canProcess = restaurantManager.canProcessOrder(1);

        assertTrue(canProcess); 
    }

    @Test
    void testCanProcessOrder_CannotProcess() {
        Restaurant restaurant = new Restaurant();
        restaurant.setOrderProcessingCapacity(5);
        restaurant.getOrders().add(new Order()); 
        restaurant.getOrders().add(new Order()); 
        restaurant.getOrders().add(new Order()); 
        restaurant.getOrders().add(new Order()); 
        restaurant.getOrders().add(new Order()); 

        when(cache.getRestaurant(1)).thenReturn(restaurant);

        boolean canProcess = restaurantManager.canProcessOrder(1);

        assertFalse(canProcess); 
    }

    @Test
    void testUpdateRestaurantMenu() {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        Menuitem item1 = new Menuitem();
        item1.setItemName("Pizza");
        Menuitem item2 = new Menuitem();
        item2.setItemName("Burger");

        List<Menuitem> menu = Arrays.asList(item1, item2);

        when(cache.getAvailableItems()).thenReturn(Collections.emptyList()); 

        restaurantManager.updateRestaurantMenu(1, menu);

        verify(cache, times(2)).addItemsToSystem(menu); 
    }

    @Test
    void testGetRestaurantMenu() {
        Menuitem pizza = new Menuitem();
        pizza.setItemName("Pizza");
        Restaurant restaurant = new Restaurant();
        restaurant.setMenu(Collections.singletonList(pizza));

        when(cache.getRestaurant(1)).thenReturn(restaurant);

        List<Menuitem> menu = restaurantManager.getRestaurantMenu(1);

        assertNotNull(menu);
        assertEquals(1, menu.size());
        assertEquals("Pizza", menu.get(0).getItemName());
    }

    @Test
    void testGetAllRestaurants() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantId(2);

        Map<Integer, Restaurant> allRestaurants = Map.of(1, restaurant1, 2, restaurant2);
        when(cache.getAllRestaurants()).thenReturn(allRestaurants);

        List<Restaurant> result = restaurantManager.getAllRestaurants();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetSelectionStrategyOptions() {
        String[] options = restaurantManager.getSelectionStrategyOptions();

        assertNotNull(options);
        assertEquals(2, options.length); 
    }

    @Test
    void testGetCurrentSelectionStrategy() {
        when(cache.getSelectionStrategy()).thenReturn(0); 

        int strategy = restaurantManager.getCurrentSelectionStrategy();

        assertEquals(0, strategy); 
    }

    @Test
    void testSetSelectionStrategy() {
        restaurantManager.setSelectionStrategy(1); 

        verify(cache, times(1)).setSelectionStrategy(1); 
    }
    
}
