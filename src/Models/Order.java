package Models;
import Enums.OrderStatus;
import Enums.OrderType;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDateTime;
import java.util.*;
import static java.util.stream.Collectors.toList;

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
    private Preparation preparation;
    private float totalPrice;
    private final OrderType orderType;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    //do kompozycji
    private String orderName;
    private final List<Delivery> deliveries = new ArrayList<>();
    private static Set<Delivery> allDeliveries = new HashSet<>();
    private boolean shoppingCart = true;
    //=========================================================
    // ASSOCIATIONS
    //=========================================================
    private Client client;
    private final List<Product> products = new ArrayList<>();

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

    public boolean isShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(boolean shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    //Do kompozycji


    public void addPart(Delivery delivery) throws Exception {
        if(!deliveries.contains(delivery)) {
            // Check if the part has been already added to any wholes
            if(allDeliveries.contains(delivery)) {
                throw new Exception("The part is already connected with a whole!");
            }

            deliveries.add(delivery);

            // Store on the list of all parts
            allDeliveries.add(delivery);
        }
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

    public void addProduct(Product... products) {
        for (Product product : products) {
            addProduct(product);
        }
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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getTotalPrice() {
        setTotalPrice((float) countOrderValue());
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
        Delivery d = Delivery.create(this);
        deliveries.add(d);
        return d;
    }
    public void addDelivery(Delivery d) {
        if (d.getOrder() != null && d.getOrder() != this) {
            throw new IllegalStateException("Delivery already belongs to another Order!");
        }
        if (!deliveries.contains(d)) {
            deliveries.add(d);
            d.setOrder(this);
        }
    }
    public void removeDelivery(Delivery d) {
        if (deliveries.remove(d)) {
            d.setOrder(null); // OR: „destroy” logic in stricter version
        }
    }
    public List<Delivery> getDeliveries() {
        return deliveries;
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

    public Preparation getPreparation() {
        return preparation;
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
    public void startPreparation(Barista barista) {
        if (orderStatus != OrderStatus.ACCEPTED) {throw new IllegalStateException("Only accepted orders can be prepared.");}
        orderStatus = OrderStatus.PREPARING;
//        preparation = new Preparation(barista, this);
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

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
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
    public boolean isNew() {
        return orderStatus == OrderStatus.NEW;
    }
    public static List<Order> getActiveOrders() {
        return getOrderExtent().stream()
                .filter(order -> !order.isCompleted())
                .filter(order -> !order.isCancelled())
                .toList();
    }
    public static List<Order> getPreparingOrders() {
        return getOrderExtent().stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.PREPARING)
                .toList();
    }
    public static List<Order> getNewOrders() {
        return getOrderExtent().stream().filter(order -> order.getOrderStatus() == OrderStatus.NEW).toList();
    }
    public static List<Order> getCompletedOrders() {
        return getOrderExtent().stream()
                .filter(Order::isCompleted)
                .toList();
    }
    public static double calculateTotalIncome() {
        return getOrderExtent().stream()
                .mapToDouble(Order::countOrderValue)
                .sum();
    }
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

    public void serveOrder() {
        System.out.println(orderStatus);
        if (orderStatus != OrderStatus.READY) {
            throw new IllegalStateException(
                    "Only READY orders can be served."
            );
        }
        orderStatus = OrderStatus.SERVED;
        System.out.println("Current status = " + orderStatus);
        try {
            Delivery.createDelivery(this, "Delivery");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Order> getReadyOrders() {
        List<Order> result = new ArrayList<>();
        for (Order order : getOrderExtent()) {
            if (order.getOrderStatus() == OrderStatus.READY) {
                result.add(order);
            }
        }
        return result;
    }
}