package Models;

import Models.Order;
import SecondaryClasses.ObjectPlus;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class Delivery implements Serializable {
    public static List<Delivery> getDeliveryExtent() {
        return (List<Delivery>) (List<?>) ObjectPlus.getExtent(Delivery.class);
    }
    private static int counter = 1;

    private final int deliveryID;
    private final Order order;

    private String address;
    private String deliveryDate;
    private Waiter waiter;

    private Delivery(Order order, String address, String deliveryDate) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        this.deliveryID = counter++;
        this.order = order;
        this.address = address;
        this.deliveryDate = deliveryDate;
    }

    public static Delivery createDelivery(Order order, String address, String deliveryDate) {
        return new Delivery(order, address, deliveryDate);
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public Order getOrder() {
        return order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public static void rebuildCounter(Collection<Order> orders) {
        int maxId = 0;
        for (Order order : orders) {
            Delivery delivery = order.getDelivery();
            if (delivery != null && delivery.deliveryID > maxId)
                maxId = delivery.deliveryID;
        }
        counter = maxId + 1;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + deliveryID +
                ", address='" + address + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                '}';
    }
}