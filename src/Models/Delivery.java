package Models;

import java.util.ArrayList;
import java.util.List;

public class Delivery {
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addDelivery" which adds instance of {@linkplain Delivery} to extent collection</br>
     * <br>Private Static method "removeDelivery" which removes instance of {@linkplain Delivery} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Delivery} line by line.</br>
     */
//    @Override
//    public String toString() {
//        return "Delivery: " + deliveryID;
//    }
    private static List<Delivery> extent = new ArrayList<>();
    private static void addDelivery(Delivery delivery) {
        extent.add(delivery);
    }
    private static void removeDelivery(Delivery delivery) {
        extent.remove(delivery);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Delivery.class.getName());
        for (Delivery delivery : extent) {
            System.out.println(delivery);
        }
    }
    //EXTENT SESSION END
    //FIELDS SESSION START
    /**Simple, Single, Required, Class, Concrete Attribute "counter" typed {@linkplain Integer}
     */
    private static int counter = 1;
    /**Simple, Single, Required, Object, Concrete Attribute "deliveryID" typed {@linkplain Integer}
     */
    int deliveryID;
    /**Simple, Single, Required, Object, Concrete Attribute "clientID" typed {@linkplain Integer}
     */
//    int clientID;
//    /**Simple, Single, Required, Object, Concrete Attribute "orderID" typed {@linkplain Integer}
//     */
//    int orderID;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Delivery(Order order) {
        this.order = order;
        this.deliveryID = counter++;
//        this.clientID = clientID;
//        this.orderID = orderID;
    }
    public Delivery(){
        this.deliveryID = counter;
        counter++;
    }
    static Delivery create(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Delivery must belong to Order!");
        }
        return new Delivery(order);
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END
    //METHODS SESSION START
    //METHODS SESSION END
    //COMPOSITION
    private Order order;
    public void setOrder(Order o) {
        if (this.order == o) return;
        this.order = o;
        if (o != null && !o.getDeliveries().contains(this)) {
            o.addDelivery(this);
        }
    }
    public Order getOrder() {
        return order;
    }
    @Override
    public String toString() {
        return "Delivery: " + deliveryID +
                (order != null ? ", order=" + order.getOrderID() : ", NO ORDER");
    }

    public void assignOrder(Order order){

        setOrder(order);

    }

    public static Delivery registerDelivery(Order order){

        return Delivery.create(order);

    }
}
