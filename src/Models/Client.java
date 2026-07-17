package Models;
import Enums.Citizenship;
import Enums.OrderType;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.*;
public class Client extends Person {
    private static final long serialVersionUID = 1L;
    private int clientID;
    private boolean hasClubCard;
    private int satisfactionOfTheService;
    private Citizenship citizenship;
    private Address address;
    private final Map<Integer, Order> orders =
            new TreeMap<>();
    public Client() {
        super();
    }
    public Client(String name, String surname, LocalDate dateOfBirth, Sex sex, boolean hasClubCard) {
        super(name, surname, dateOfBirth, sex);
        this.clientID = Person.getCounter();
        this.hasClubCard = hasClubCard;
    }
    public Client(String name, String surname, LocalDate birthDate, Sex sex, Address address, boolean hasClubCard, Citizenship citizenship) {
        super(name, surname, birthDate, sex);
        this.address = address;
        this.hasClubCard = hasClubCard;
        this.citizenship = citizenship;
    }
    public Client(String name,
                  String surname,
                  LocalDate dateOfBirth,
                  Sex sex,
                  boolean hasClubCard,
                  Citizenship citizenship,
                  int clientID) {
        super(name, surname, dateOfBirth, sex);
        this.clientID = clientID;
        this.hasClubCard = hasClubCard;
        this.citizenship = citizenship;
    }
    @SuppressWarnings("unchecked")
    public static List<Client> getClientExtent() {
        return (List<Client>)(List<?>) ObjectPlus.getExtent(Client.class);
    }
    public static boolean clientExists(String name,
                                       String surname,
                                       LocalDate birthDate,
                                       Sex sex,
                                       Citizenship citizenship) {
        for (Client client : getClientExtent()) {
            if (client.getPersonName().equalsIgnoreCase(name) &&
                client.getPeronSurname().equalsIgnoreCase(surname) &&
                client.getPersonDateOfBirth().equals(birthDate) &&
                client.getPersonSex() == sex &&
                    Objects.equals(
                            client.getCitizenship().orElse(null),
                            citizenship)) {
                return true;
            }
        }
        return false;
    }
    //GETTERS
    public boolean hasClubCard() {
        return hasClubCard;
    }
    public Address getAddress() {
        return address;
    }
    public Optional<Citizenship> getCitizenship() {
        return Optional.ofNullable(citizenship);
    }
    public Collection<Order> getOrders() {
        return Collections.unmodifiableCollection(orders.values());
    }
    @Override
    public String getPrivileges() {
        return "CLIENT";
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public void setSatisfactionOfTheService(int satisfaction) {
        if (satisfaction < 1 || satisfaction > 5)
            throw new IllegalArgumentException("Satisfaction must be between 1 and 5.");
        this.satisfactionOfTheService = satisfaction;
    }

    public void addOrder(Order order) {
        Objects.requireNonNull(order);
        if (orders.containsKey(order.getOrderID()))
            return;
        orders.put(order.getOrderID(), order);
        order.setClient(this);
    }

    @Override
    public String toString() {
        return String.format(
                "Client{id=%d, name='%s %s', clubCard=%s, citizenship=%s}",
                clientID,
                personName,
                peronSurname,
                hasClubCard,
                citizenship
        );
    }

    public Order getShoppingCart() {
        for (Order order : orders.values()) {
            if (order.isShoppingCart())
                return order;
        }
        return createNewShoppingCart();
    }

    public Order createNewShoppingCart() {
        Order cart = Order.createOrder(this, OrderType.Liquid);
        return cart;
    }
    public int countOrders() {
        return getOrders().size() - 1;
    }

    public static Client createFromEmployee(Employee employee, boolean hasClubCard, Citizenship citizenship) {
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null.");

        return new Client(employee.getPersonName(), employee.getPeronSurname(), employee.getPersonDateOfBirth(),
                employee.getPersonSex(), hasClubCard, citizenship, Person.getCounter()
        );
    }
    public static boolean isClient(Person person) {
        return getClientExtent().stream().anyMatch(client ->
                client.getPersonName().equalsIgnoreCase(person.getPersonName())
                        && client.getPeronSurname().equalsIgnoreCase(person.getPeronSurname())
                        && client.getPersonDateOfBirth().equals(person.getPersonDateOfBirth())
                        && client.getPersonSex() == person.getPersonSex()
        );
    }
}