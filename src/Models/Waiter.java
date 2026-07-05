package Models;
import Enums.AllPersonTypes;
import Enums.OrderStatus;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
public class Waiter extends Employee {
    private static final long serialVersionUID = 1L;
    // ATTRIBUTES
    private float waitersTip;
    private int waitersGrade;
    private List<Integer> waitersGrades = new ArrayList<>();
    private final EnumSet<AllPersonTypes> personKind = EnumSet.of(AllPersonTypes.Waiter);
    private final List<Service> services = new ArrayList<>();
    public List<Integer> getWaitersGrades() {
        return waitersGrades;
    }
    private final List<Delivery> servedDeliveries = new ArrayList<>();
    public List<Service> getServices() {
        return Collections.unmodifiableList(services);
    }
    // CONSTRUCTORS
    public Waiter() {
        super();
    }
    public Waiter(String name, String surname, LocalDate birthDate, Sex sex, float salary) {
        super(name, surname, birthDate, sex, salary);
    }
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Waiter> getWaiterExtent() {
        return (List<Waiter>) (List<?>) ObjectPlus.getExtent(Waiter.class);
    }
    // GETTERS
    public List<Delivery> getServedDeliveries() {
        return Collections.unmodifiableList(servedDeliveries);
    }
    // BUSINESS METHODS
    @Override
    public String getPrivileges() {
        return "WAITER";
    }
    public void serveOrder(Order order) throws Exception {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null.");
        if (order.getOrderStatus() != OrderStatus.READY)
            throw new IllegalStateException("Order is not ready.");
        order.setStatus(OrderStatus.SERVED);
        Delivery delivery = Delivery.createDelivery(order, "Delivery #" + order.getOrderID());
        delivery.setWaiter(this);
        System.out.println("Order served.");
    }
    private int calculateAverageGrade() {
        if (waitersGrades.isEmpty())
            return 0;
        int sum = 0;
        for (Integer grade : waitersGrades)
            sum += grade;
        return Math.round((float) sum / waitersGrades.size());
    }
    @Override
    public String toString() {
        return String.format(
                "Waiter{id=%d, name='%s %s', salary=%.2f, grade=%d, tips=%.2f}", employeeID, personName, peronSurname,
                employeeSalary, waitersGrade, waitersTip);}
    public void setGrade(int satisfaction) {
        int average = 0;
        for(int a : waitersGrades)
            average += a;
         waitersGrade = average / waitersGrades.size();
    }
    public void addService(Service service) {
        if (service == null)
            throw new IllegalArgumentException();
        if (!services.contains(service)) {
            services.add(service);
        }
    }
    public void addDelivery(Delivery delivery) {
        if (delivery == null)
            throw new IllegalArgumentException("Delivery cannot be null.");
        if (!servedDeliveries.contains(delivery)) {
            servedDeliveries.add(delivery);
            if (delivery.getWaiter() != this)
                delivery.setWaiter(this);
        }
    }
    public int countServedOrders() {
        return servedDeliveries.size();
    }

    public void receivePayment(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        order.receivePayment();
    }

}