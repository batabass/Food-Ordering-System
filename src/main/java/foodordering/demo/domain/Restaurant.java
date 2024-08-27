package foodordering.demo.domain;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.concurrent.LinkedBlockingQueue;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Restaurant {

    private String name;
    private int restaurantId;
    private List<Menuitem> menu;
    private int orderProcessingCapacity;
    private double rating;
    @JsonIgnore
    private BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    public String getName() {
        return name;
    }

    public Queue<Order> getOrders() {
        return orders;
    }
    public int getRestaurantId() {
        return restaurantId;
    }
    public List<Menuitem> getMenu() {
        return menu;
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public int getOrderProcessingCapacity() {
        return orderProcessingCapacity;
    }
    public double getRating() {
        return rating;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMenu(List<Menuitem> menu) {
        this.menu = menu;
    }
    public void setOrderProcessingCapacity(int orderProcessingCapacity) {
        this.orderProcessingCapacity = orderProcessingCapacity;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    
}
