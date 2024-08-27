package foodordering.demo.implemantation;



import java.util.List;
import java.util.stream.Collectors;

import foodordering.demo.domain.Menuitem;
import foodordering.demo.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import foodordering.demo.domain.Restaurant;

@RestController
@RequestMapping("/api/orderFood")
public class FoodOrderingController {

    @Autowired
    private FoodOrderingSystem foodOrderingSystem;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) throws Exception{
       
        Integer orderId = foodOrderingSystem.placeOrder(order);
        
        return ResponseEntity.ok("OrderId:"+orderId);
    }

    @GetMapping("/showAvailableItems")
    public ResponseEntity<List<String>> showAvailableItems() {
        List<Menuitem> allItems = foodOrderingSystem.showAvailableItems();
        List<String> items = allItems.stream().map(Menuitem::getItemName).map(String::toLowerCase).collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}/orderProcessingState")
    public ResponseEntity<String> orderProcessingState(@PathVariable int id) {
        String state = foodOrderingSystem.orderProcessingState(id);
        return ResponseEntity.ok(state);
    }
    
}
