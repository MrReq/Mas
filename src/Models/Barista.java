package Models;
import Enums.CoffeeCountry;
import Enums.OrderStatus;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.time.LocalDate;
import java.util.*;

public class Barista extends Employee {
    private static final long serialVersionUID = 1L;
    /**
     * Optional attribute.
     * Favourite coffee bean origin.
     */
    private CoffeeCountry favouriteCoffeeCountry;
    private List<Preparation> preparations = new ArrayList<>();
    private final Map<Integer, Order> orders = new HashMap<>();
    // CONSTRUCTORS
    public Barista() {
        super();
    }
    public Barista(String name,
                   String surname,
                   LocalDate dateOfBirth,
                   Sex sex,
                   float salary,
                   CoffeeCountry favouriteCoffeeCountry) {
        super(name, surname, dateOfBirth, sex, salary);
        this.favouriteCoffeeCountry = favouriteCoffeeCountry;
    }
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Barista> getBaristaExtent() {
        return (List<Barista>) (List<?>) ObjectPlus.getExtent(Barista.class);
    }
    // GETTERS
    public CoffeeCountry getFavouriteCoffeeCountry() {
        return favouriteCoffeeCountry;
    }
    @Override
    public String getPrivileges() {
        return "BARISTA";
    }
    // SERIALIZATION
    // EXTENT
    //METHODS
    @Override
    public String toString() {
        return String.format(
                "Barista{id=%d, name='%s %s', salary=%.2f, favouriteCoffeeCountry=%s}", employeeID, personName, peronSurname,
                employeeSalary, favouriteCoffeeCountry);
    }

    public void acceptOrder(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null.");

            for (Product product : order.getProducts()) {
                if (!product.isProductAvailability()) {
                    throw new IllegalStateException(
                            "Some products are unavailable."
                    );
                }
            }
            order.acceptOrder();
            addOrder(order);
        }
    public void startPreparing(Order order){
        Preparation preparation = new Preparation(this, order);
        order.setPreparation(preparation);
        order.startPreparation();
    }
    public void prepareDrink(Drink drink) {
        if (drink == null)
            throw new IllegalArgumentException("Drink cannot be null.");
        System.out.println("Preparing " + drink.getProductName() + "...");
        drink.prepare();
    }

    public void markOrderAsReady(Order order) {
        if(order == null)
            throw new IllegalArgumentException("Order cannot be null.");
        order.markAsReady();
    }

    public void completeOrder(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null.");
        order.completeOrder();
    }

    public int countNewOrders() {
        return (int) Order.getOrderExtent()
                .stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.NEW)
                .count();
    }

    public int countPreparingOrders() {
        return (int) Order.getOrderExtent()
                .stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.PREPARING)
                .count();
    }

    public int countReadyOrders() {
        return (int) Order.getOrderExtent()
                .stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.READY)
                .count();
    }

    public Collection<Order> getOrders() {
        return Collections.unmodifiableCollection(
                orders.values()
        );
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }
        orders.put(order.getOrderID(), order);
    }


}