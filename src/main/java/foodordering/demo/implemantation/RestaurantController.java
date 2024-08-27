package foodordering.demo.implemantation;


import foodordering.demo.domain.Restaurant;
import foodordering.demo.domain.Menuitem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantManager restaurantManager;

    @PostMapping("/addRestaurant")
    public ResponseEntity<Void> addRestaurant(@RequestBody Restaurant restaurant) {
        List<Menuitem> menu = restaurant.getMenu();
        if(menu.size()>0)
            restaurantManager.addItemsToSystemItems(menu);
        restaurantManager.addRestaurant(restaurant);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> dropRestaurant(@PathVariable int id) {
        restaurantManager.dropRestautant(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/updateMenu")
    public ResponseEntity<Void> updateRestaurantMenu(@PathVariable int id, @RequestBody List<Menuitem> menu) {
        restaurantManager.updateRestaurantMenu(id, menu);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/getMenu")
    public ResponseEntity<List<Menuitem>> getRestaurantMenu(@PathVariable int id) {
        List<Menuitem> menu =  restaurantManager.getRestaurantMenu(id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/{id}/canProcessOrder")
    public ResponseEntity<Boolean> canProcessOrder(@PathVariable int id) {
        boolean canProcess = restaurantManager.canProcessOrder(id);
        return ResponseEntity.ok(canProcess);
    }

    @GetMapping("/getSelectionStrategyOptions")
    public ResponseEntity<String[]> getSelectionStrategyOptions() {
        String[] strategies = restaurantManager.getSelectionStrategyOptions();
        return ResponseEntity.ok(strategies);
    }

    @GetMapping("/getCurrentSelectionStrategy")
    public ResponseEntity<Integer> getCurrentSelectionStrategy() {
        int strategies = restaurantManager.getCurrentSelectionStrategy();
        return ResponseEntity.ok(strategies);
    }

    @PostMapping("/{id}/setSelectionStrategy")
    public ResponseEntity<Void> setSelectionStrategy(@PathVariable int id) {
        restaurantManager.setSelectionStrategy(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllRestaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantManager.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }


    @GetMapping("/{id}/getRestaurantDetails")
    public ResponseEntity<Restaurant> getRestaurantDetails(@PathVariable int id) {
        Restaurant restaurant = restaurantManager.getRestaurantDetails(id);
        return ResponseEntity.ok(restaurant);
    }
}
