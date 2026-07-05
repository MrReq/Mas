package Models;
import SecondaryClasses.ObjectPlus;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
public class Preparation extends ObjectPlus implements Serializable {
    private static final long serialVersionUID = 1L;
    // ATTRIBUTES
    private Barista barista;
    private Order order;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // CONSTRUCTOR

    public Preparation(Barista barista, Order order) {
        System.out.println("NEW PREPARATION -> Order " + order.getOrderID());
        if (barista == null) {
            throw new IllegalArgumentException("Barista cannot be null.");
        }
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        this.barista = barista;
        this.order = order;
        order.setPreparation(this);
        barista.addPreparation(this);
        this.startTime = LocalDateTime.now();
    }
    // EXTENT

    @SuppressWarnings("unchecked")
    public static List<Preparation> getPreparationExtent() {
        return (List<Preparation>)(List<?>) ObjectPlus.getExtent(Preparation.class);

    }
    // BUSINESS METHODS
    public void finishPreparation() {endTime = LocalDateTime.now();}
    public Duration getPreparationTime() {
        if (endTime == null) {
            return Duration.between(startTime, LocalDateTime.now());
        }
        return Duration.between(startTime, endTime);
    }
    // GETTERS
    public Barista getBarista() {
        return barista;
    }
    public Order getOrder() {
        return order;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    // OBJECT METHODS
    @Override
    public String toString() {
        return "Preparation{" + "barista=" + barista.getPersonName() +
                ", order=" + order.getOrderID() + ", started=" + startTime + ", finished=" + endTime + '}';
    }
}