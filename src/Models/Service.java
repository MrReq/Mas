package Models;
import SecondaryClasses.ObjectPlus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
public class Service extends ObjectPlus implements Serializable {

    private static final long serialVersionUID = 1L;
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Service> getServiceExtent() {
        return (List<Service>) (List<?>) ObjectPlus.getExtent(Service.class);
    }
    public static void showExtent() {
        System.out.println("===== SERVICE EXTENT =====");
        for (Service service : getServiceExtent()) {
            System.out.println(service);
        }
    }
    // ASSOCIATION
    //=========================================================
    private final Waiter waiter;
    private final Delivery delivery;
    // ATTRIBUTE OF ASSOCIATION
    private final LocalDateTime serviceDate;
    private final int deliveryTimeMinutes;
    private String note;
    // CONSTRUCTOR
    public Service(Waiter waiter, Delivery delivery, int deliveryTimeMinutes, String note) {
        if (waiter == null)
            throw new IllegalArgumentException("Waiter cannot be null.");
        if (delivery == null)
            throw new IllegalArgumentException("Delivery cannot be null.");
        if (deliveryTimeMinutes < 0)
            throw new IllegalArgumentException("Delivery time cannot be negative.");
        this.waiter = waiter;
        this.delivery = delivery;
        this.deliveryTimeMinutes = deliveryTimeMinutes;
        this.note = note;
        this.serviceDate = LocalDateTime.now();
        // automatic reverse links
        waiter.addService(this);
        delivery.addService(this);
    }
    // GETTERS
    public Waiter getWaiter() {
        return waiter;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public LocalDateTime getServiceDate() {
        return serviceDate;
    }
    public int getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    // TO STRING
    @Override
    public String toString() {
        return String.format(
                """
                Waiter: %s %s
                Delivery ID: %d
                Service date: %s
                Delivery time: %d min
                Note: %s
                """, waiter.getPersonName(), waiter.getPeronSurname(), delivery.getDeliveryID(), serviceDate,
                deliveryTimeMinutes, note == null ? "-" : note
        );
    }
}