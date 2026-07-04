package Models;
import SecondaryClasses.ObjectPlus;
import java.util.ArrayList;
import java.util.List;
public class Delivery extends ObjectPlus {
    private static final long serialVersionUID = 1L;
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addDelivery" which adds instance of {@linkplain Delivery} to extent collection</br>
     * <br>Private Static method "removeDelivery" which removes instance of {@linkplain Delivery} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Delivery} line by line.</br>
     */
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Delivery> getDeliveryExtent() {
        return (List<Delivery>) (List<?>) ObjectPlus.getExtent(Delivery.class);
    }
    private static int counter = 1;
    int deliveryID;
    //do kompozycji
    private String deliveryName;
    private Order order;
    private Delivery(Order order , String deliveryName){
        this.order = order;
        this.deliveryName = deliveryName;
    }

    public static Delivery createDelivery(Order order, String name) throws Exception {
        if(order == null) {
            throw new Exception("The given whole does not exist!");
        }
        // Create a new part
        Delivery delivery = new Delivery(order, name);
        // Add to the whole
        order.addPart(delivery);
        return delivery;
    }

    private Order order;
    private Delivery(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null."
            );
        }
        this.deliveryID = counter++;
        this.order = order;
    }
    static Delivery create(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Delivery must belong to Order!");
        }
        return new Delivery(order);
    }
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
}
