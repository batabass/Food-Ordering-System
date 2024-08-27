package foodordering.demo.domain;

import java.util.*;;
public class Order {

    private int orderId;
    private List<Menuitem> orderItems;
    private double totalPrice;
    private boolean isPicked;
    private boolean isDelivered;
    
    public int getOrderId() {
        return orderId;
    }
    public List<Menuitem> getOrderItems() {
        return orderItems;
    }
    public double getTotalPrice() {
        
        return totalPrice;
    }
    public boolean isPicked() {
        return isPicked;
    }
    public boolean isDelivered() {
        return isDelivered;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public void setOrderItems(List<Menuitem> orderItems) {
        this.orderItems = orderItems;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setPicked(boolean isPicked) {
        this.isPicked = isPicked;
    }
    public void setDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }



    
}
