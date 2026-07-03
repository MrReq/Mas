package Models;

import Enums.OrderStatus;
import Enums.OrderType;
import SecondaryClasses.ObjectPlus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Order extends ObjectPlus{
    private static final long serialVersionUID = 1L;
    //=========================================================
    // EXTENT
    //=========================================================
    @SuppressWarnings("unchecked")
    public static List<Order> getOrderExtent() {
        return (List<Order>)(List<?>) ObjectPlus.getExtent(Order.class);
    }
    public static void showExtent() {
        System.out.println("===== ORDER EXTENT =====");
        for (Order order : getOrderExtent()) {
            System.out.println(order);
        }
    }
    //=========================================================
    // FIELDS
    //=========================================================
    private static int counter = 1;
    private final int orderID;
    private final OrderType orderType;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    //=========================================================
    // ASSOCIATIONS
    //=========================================================
    private Client client;
    private final List<Product> products = new ArrayList<>();
    private final List<Delivery> deliveries = new ArrayList<>();
    //=========================================================
    // CONSTRUCTORS
    //=========================================================
    public Order(Client client,
                 OrderType orderType) {
        if (orderType == null) {
            throw new IllegalArgumentException(
                    "Order type cannot be null."
            );
        }
        this.orderID = counter++;
        this.client = client;
        this.orderType = orderType;
        this.orderStatus = OrderStatus.NEW;
        this.createdAt = LocalDateTime.now();
        if (client != null) {
            client.addOrder(this);
        }
    }
    public static Order createOrder(Client client,
                                    OrderType type) {
        if (client == null) {
            throw new IllegalArgumentException(
                    "Client cannot be null."
            );
        }
        return new Order(client, type);
    }
    //=========================================================
    // PRODUCTS
    //=========================================================
    public void addProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException();
        }

        products.add(product);
    }
    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }
        products.remove(product);
    }
    public void clearProducts() {
        products.clear();
    }
    public boolean isEmpty() {
        return products.isEmpty();
    }
    public int getProductsCount() {
        return products.size();
    }
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
    //=========================================================
    // PRICE
    //=========================================================
    public double countOrderValue() {
        return products.stream()
                .mapToDouble(Product::getProductCost)
                .sum();
    }
    public float getTotalPrice() {
        return (float) countOrderValue();
    }
    //=========================================================
    // CLIENT
    //=========================================================
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        if (this.client == client) {
            return;
        }
        this.client = client;
        if (client != null) {
            client.addOrder(this);
        }
    }
    //=========================================================
    // DELIVERY (COMPOSITION)
    //=========================================================
    public Delivery addDelivery() {
        Delivery delivery = Delivery.create(this);
        deliveries.add(delivery);
        return delivery;
    }
    public void addDelivery(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException(
                    "Delivery cannot be null."
            );
        }
        if (delivery.getOrder() != null &&
                delivery.getOrder() != this) {
            throw new IllegalStateException(
                    "Delivery already belongs to another order."
            );
        }
        if (!deliveries.contains(delivery)) {
            deliveries.add(delivery);
            delivery.setOrder(this);
        }
    }
    public void removeDelivery(Delivery delivery) {
        if (delivery == null) {
            return;
        }
        if (deliveries.remove(delivery)) {
            delivery.setOrder(null);
        }
    }
    public List<Delivery> getDeliveries() {
        return Collections.unmodifiableList(deliveries);
    }
    //=========================================================
    // GETTERS
    //=========================================================
    public int getOrderID() {
        return orderID;
    }
    public OrderType getOrderType() {
        return orderType;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    //=========================================================
    // BUSINESS METHODS
    //=========================================================

    /**
     * Boss/Barista accepts order.
     */
    public void acceptOrder() {
        if (orderStatus != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "Only NEW orders can be accepted."
            );
        }
        orderStatus = OrderStatus.ACCEPTED;
    }
    /**
     * Barista starts preparing the order.
     */
    public void startPreparation() {
        orderStatus = OrderStatus.PREPARING;
    }
    /**
     * Barista finished preparing.
     */
    public void markAsReady(){

        orderStatus = OrderStatus.READY;

    }

    public void completeOrder() {
        if (orderStatus != OrderStatus.PREPARING) {
            throw new IllegalStateException(
                    "Only In preparing order can be completed."
            );
        }
        orderStatus = OrderStatus.READY;
    }
    /**
     * Waiter delivers the order.
     */
    public void deliver() {
        if (orderStatus != OrderStatus.READY) {
            throw new IllegalStateException(
                    "Order is not READY."
            );
        }
        orderStatus = OrderStatus.DELIVERED;
    }
    /**
     * Cancel order.
     */
    public void cancelOrder() {
        if (orderStatus == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Delivered order cannot be cancelled."
            );
        }
        orderStatus = OrderStatus.CANCELLED;
    }
    /**
     * Generic status update.
     */
    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Status cannot be null."
            );
        }
        this.orderStatus = status;
    }
    /**
     * Returns true if order is completed.
     */
    public boolean isCompleted() {
        return orderStatus == OrderStatus.DELIVERED;
    }
    /**
     * Returns true if order has been cancelled.
     */
    public boolean isCancelled() {
        return orderStatus == OrderStatus.CANCELLED;
    }
    /**
     * Returns true if order is currently being prepared.
     */
    public boolean isPreparing() {
        return orderStatus == OrderStatus.PREPARING;
    }
    /**
     * Returns true if order is ready.
     */
    public boolean isReady() {
        return orderStatus == OrderStatus.READY;
    }
    /**
     * Returns true if order has been accepted.
     */
    public boolean isAccepted() {
        return orderStatus == OrderStatus.ACCEPTED;
    }
    /**
     * Returns true if order is new.
     */
    public boolean isNew() {

        return orderStatus == OrderStatus.NEW;

    }

//Static methods

    /**
     * Returns all active orders.
     */
    public static List<Order> getActiveOrders() {
        return getOrderExtent().stream()
                .filter(order -> !order.isCompleted())
                .filter(order -> !order.isCancelled())
                .toList();
    }

    /**
     * Returns all completed orders.
     */
    public static List<Order> getCompletedOrders() {
        return getOrderExtent().stream()
                .filter(Order::isCompleted)
                .toList();
    }

    /**
     * Returns total income.
     */
    public static double calculateTotalIncome() {
        return getOrderExtent().stream()
                .mapToDouble(Order::countOrderValue)
                .sum();
    }
    /**
     * Returns total number of ordered products.
     */
    public int getNumberOfProducts() {
        return products.size();
    }

    public static Order findById(int orderID) {
        System.out.println("Searching for order ID = " + orderID);
        System.out.println("Orders in extent: " + getOrderExtent().size());
        for (Order order : getOrderExtent()) {
            System.out.println("Found order: " + order.getOrderID());
            if (order.getOrderID() == orderID) {
                System.out.println("MATCH!");
                return order;
            }
        }
        System.out.println("NOT FOUND");
        return null;
    }

    @Override
    public String toString() {

        return String.format(
                """
                Order #%d
                Type: %s
                Status: %s
                Client: %s
                Products: %d
                Value: %.2f PLN
                Created: %s
                """,
                orderID,
                orderType,
                orderStatus,
                client == null
                        ? "No client"
                        : client.getPersonName()
                        + " "
                        + client.getPeronSurname(),
                products.size(),
                countOrderValue(),
                createdAt
        );
    }

    public static void rebuildCounter() {

        int maxId = 0;

        for (Order order : getOrderExtent()) {

            if (order.getOrderID() > maxId) {
                maxId = order.getOrderID();
            }

        }

        counter = maxId + 1;
    }
}