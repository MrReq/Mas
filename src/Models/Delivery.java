package Models;

import SecondaryClasses.ObjectPlus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Delivery extends ObjectPlus {

    private static final long serialVersionUID = 1L;

    //=========================================================
    // EXTENT
    //=========================================================
    @SuppressWarnings("unchecked")
    public static List<Delivery> getDeliveryExtent() {
        return (List<Delivery>) (List<?>) ObjectPlus.getExtent(Delivery.class);
    }

    public static void showExtent() {
        System.out.println("===== DELIVERY EXTENT =====");
        for (Delivery delivery : getDeliveryExtent()) {
            System.out.println(delivery);
        }
    }

    //=========================================================
    // FIELDS
    //=========================================================
    private static int counter = 1;

    private final int deliveryID;

    private  String deliveryName;

    /**
     * Delivery jest częścią Order.
     * Nie może zmienić właściciela.
     */
    private  Order order;
    private final List<Service> services = new ArrayList<>();
    private Waiter waiter;
    //=========================================================
    // CONSTRUCTOR
    //=========================================================

    /**
     * Prywatny konstruktor.
     * Delivery nie może zostać utworzone poza createDelivery().
     */
    private Delivery(Order order, String deliveryName) {
        this.deliveryID = counter++;
        this.order = order;
        this.deliveryName = deliveryName;
    }

    public Delivery(Order order) {
        this.order = order;
        this.deliveryID = counter++;
//        this.clientID = clientID;
//        this.orderID = orderID;
    }

    public void setWaiter(Waiter waiter) {
        if (this.waiter == waiter) {
            return;
        }
        this.waiter = waiter;
        if (waiter != null &&
                !waiter.getServedDeliveries().contains(this)) {
            waiter.addDelivery(this);
        }
    }

    //=========================================================
    // FACTORY METHOD
    //=========================================================

    /**
     * Jedyna metoda tworzenia Delivery.
     */
    public static Delivery createDelivery(Order order,
                                          String deliveryName) throws Exception {

        if (order == null) {
            throw new Exception("Order cannot be null.");
        }

        Delivery delivery = new Delivery(order, deliveryName);

        // dodanie do całości
        order.addPart(delivery);

        return delivery;
    }

    public void setOrder(Order o) {
        if (this.order == o) return;
        this.order = o;
        if (o != null && !o.getDeliveries().contains(this)) {
            o.addDelivery(this);
        }
    }

    static Delivery create(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Delivery must belong to Order!");
        }
        return new Delivery(order);
    }


    //=========================================================
    // GETTERS
    //=========================================================

    public int getDeliveryID() {
        return deliveryID;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public Order getOrder() {
        return order;
    }
    public Waiter getWaiter() {return waiter;}

    //=========================================================
    // TO STRING
    //=========================================================

    @Override
    public String toString() {
        return "Delivery #" + deliveryID +
                " | Name: " + deliveryName +
                " | Order: " + order.getOrderID();
    }

    //=========================================================
    // COUNTER REBUILD
    //=========================================================

    public static void rebuildCounter() {
        int max = 0;
        for (Delivery delivery : getDeliveryExtent()) {
            if (delivery.deliveryID > max) {
                max = delivery.deliveryID;
            }
        }
        counter = max + 1;
    }
    public void addService(Service service) {
        if (service == null)
            throw new IllegalArgumentException();
        if (!services.contains(service)) {
            services.add(service);
        }
    }
    public List<Service> getServices() {
        return Collections.unmodifiableList(services);
    }
}