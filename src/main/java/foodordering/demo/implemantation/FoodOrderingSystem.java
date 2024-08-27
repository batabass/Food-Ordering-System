package foodordering.demo.implemantation;


import java.util.*;
import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;

public interface FoodOrderingSystem {

    
public List<Menuitem> showAvailableItems();
public void setRestaurantSelectionStrategy(boolean byRating,boolean byPrice);
public int placeOrder(Order order) throws Exception;
public String orderProcessingState(int orderId);


    
}
