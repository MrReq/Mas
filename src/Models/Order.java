package Models;

import Enums.OrderStatus;
import Enums.OrderType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Order {
    //=========================================================
    // EXTENT
    //=========================================================
    private static final List<Order> extent = new ArrayList<>();
    private static void addOrder(Order order) {
        extent.add(order);
    }
    private static void removeOrder(Order order) {
        extent.remove(order);
    }
    public static List<Order> getExtent() {
        return Collections.unmodifiableList(extent);
    }
    public static void showExtent() {
        System.out.println("Extent of " + Order.class.getSimpleName());
        extent.forEach(System.out::println);
    }
    //=========================================================
    // FIELDS
    //=========================================================
    private static int counter = 1;
    private final int orderID;
    private final OrderType orderType;
    private OrderStatus status;
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
        this.status = OrderStatus.NEW;
        this.createdAt = LocalDateTime.now();
        addOrder(this);
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
            throw new IllegalArgumentException(
                    "Product cannot be null."
            );
        }
        if (products.contains(product)) {
            return;
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
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    //=========================================================
    // BUSINESS METHODS
    //=========================================================
    /**
     * Returns current order status.
     */
    public OrderStatus checkOrderStatus() {
        return status;
    }
    /**
     * Boss/Barista accepts order.
     */
    public void acceptOrder() {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "Only NEW orders can be accepted."
            );
        }
        status = OrderStatus.ACCEPTED;
    }
    /**
     * Barista starts preparing the order.
     */
    public void startPreparation() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException(
                    "Order must be ACCEPTED first."
            );
        }
        status = OrderStatus.PREPARING;
    }
    /**
     * Barista finished preparing.
     */
    public void markAsReady() {
        if (status != OrderStatus.PREPARING) {
            throw new IllegalStateException(
                    "Order is not being prepared."
            );
        }
        status = OrderStatus.READY;
    }
    /**
     * Waiter delivers the order.
     */
    public void deliver() {
        if (status != OrderStatus.READY) {
            throw new IllegalStateException(
                    "Order is not READY."
            );
        }
        status = OrderStatus.DELIVERED;
    }
    /**
     * Cancel order.
     */
    public void cancelOrder() {
        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Delivered order cannot be cancelled."
            );
        }
        status = OrderStatus.CANCELLED;
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
        this.status = status;
    }
    /**
     * Returns true if order is completed.
     */
    public boolean isCompleted() {
        return status == OrderStatus.DELIVERED;
    }
    /**
     * Returns true if order has been cancelled.
     */
    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }
    /**
     * Returns true if order is currently being prepared.
     */
    public boolean isPreparing() {
        return status == OrderStatus.PREPARING;
    }
    /**
     * Returns true if order is ready.
     */
    public boolean isReady() {
        return status == OrderStatus.READY;
    }
    /**
     * Returns true if order has been accepted.
     */
    public boolean isAccepted() {
        return status == OrderStatus.ACCEPTED;
    }
    /**
     * Returns true if order is new.
     */
    public boolean isNew() {

        return status == OrderStatus.NEW;

    }

//Static methods

    /**
     * Returns all active orders.
     */
    public static List<Order> getActiveOrders() {
        return extent.stream()
                .filter(order -> !order.isCompleted())
                .filter(order -> !order.isCancelled())
                .toList();
    }

    /**
     * Returns all completed orders.
     */
    public static List<Order> getCompletedOrders() {
        return extent.stream()
                .filter(Order::isCompleted)
                .toList();
    }

    /**
     * Returns total income.
     */
    public static double calculateTotalIncome() {
        return extent.stream()
                .mapToDouble(Order::countOrderValue)
                .sum();
    }
    /**
     * Returns total number of ordered products.
     */
    public int getNumberOfProducts() {
        return products.size();
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
                status,
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
}