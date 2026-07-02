package Models;

import Enums.AllPersonTypes;
import Enums.Citizenship;
import Enums.OrderType;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Client extends Person {
    // ====================================================ATTRIBUTES====================================================
    private int clientID;

    private boolean hasClubCard;

    /**
     * Service satisfaction (1-5)
     */
    private int satisfactionOfTheService;

    /**
     * Optional attribute.
     * Null means that citizenship has not been specified.
     */
    private Citizenship citizenship;

    /**
     * Overlapping
     */
    private final EnumSet<AllPersonTypes> personKind =
            EnumSet.of(AllPersonTypes.Client);

    /**
     * Qualified association Client -> Order
     */
    private final Map<Integer, Order> orders =
            new TreeMap<>();

//====================================================CONSTRUCTORS====================================================

    public Client() {
        super();
    }

    public Client(String name,
                  String surname,
                  LocalDate dateOfBirth,
                  Sex sex,
                  boolean hasClubCard) {

        super(name, surname, dateOfBirth, sex);

        this.clientID = Person.getCounter();

        this.hasClubCard = hasClubCard;
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


//==================================================== EXTENT====================================================


    @SuppressWarnings("unchecked")
    public static List<Client> getClientExtent() {
        return (List<Client>)(List<?>) ObjectPlus.getExtent(Client.class);
    }
    /**
     * Checks whether identical client already exists.
     */
    public static boolean clientExists(String name,
                                       String surname,
                                       LocalDate birthDate,
                                       Sex sex,
                                       Citizenship citizenship) {
        for (Client client : getClientExtent()) {
            if (client.getPersonName().equalsIgnoreCase(name)
                    &&
                    client.getPeronSurname().equalsIgnoreCase(surname)
                    &&
                    client.getPersonDateOfBirth().equals(birthDate)
                    &&
                    client.getPersonSex() == sex
                    &&
                    Objects.equals(
                            client.getCitizenship().orElse(null),
                            citizenship)) {
                return true;
            }
        }
        return false;
    }
    // ====================================================GETTERS====================================================

    public int getClientID() {
        return clientID;
    }

    public boolean hasClubCard() {
        return hasClubCard;
    }

    public int getSatisfactionOfTheService() {
        return satisfactionOfTheService;
    }

    public Optional<Citizenship> getCitizenship() {
        return Optional.ofNullable(citizenship);
    }

    public EnumSet<AllPersonTypes> getPersonKind() {
        return EnumSet.copyOf(personKind);
    }

    public Collection<Order> getOrders() {
        return Collections.unmodifiableCollection(
                orders.values()
        );
    }
    @Override
    public String getPrivileges() {
        return "CLIENT";
    }
    public void giveSatisfactionLevel(Waiter waiter,
                                      int satisfaction) {
        Objects.requireNonNull(waiter);
        setSatisfactionOfTheService(satisfaction);
        waiter.getWaitersGrades().add(satisfaction);
        waiter.setGrade(satisfaction);
    }
    // ====================SETTERS=================
    public void setHasClubCard(boolean hasClubCard) {
        this.hasClubCard = hasClubCard;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    public void setSatisfactionOfTheService(int satisfaction) {
        if (satisfaction < 1 || satisfaction > 5) {
            throw new IllegalArgumentException(
                    "Satisfaction must be between 1 and 5."
            );
        }
        this.satisfactionOfTheService = satisfaction;
    }

    // BUSINESS METHODS



    // QUALIFIED ASSOCIATION


    public void addOrder(Order order) {
        Objects.requireNonNull(order);
        if (orders.containsKey(order.getOrderID()))
            return;
        orders.put(order.getOrderID(), order);
        order.setClient(this);
    }

    public void removeOrder(Order order) {
        if (order == null)
            return;
        orders.remove(order.getOrderID());
    }

    public Order findOrder(int orderID) throws Exception {
        Order order = orders.get(orderID);
        if (order == null) {
            throw new Exception(
                    "Order with ID " + orderID + " not found."
            );
        }
        return order;
    }

    // SERIALIZATION
    @Override
    protected void write(DataOutputStream stream)
            throws IOException {
        super.write(stream);
        stream.writeInt(clientID);
        stream.writeBoolean(hasClubCard);
        stream.writeInt(satisfactionOfTheService);
        stream.writeBoolean(citizenship != null);
        if (citizenship != null) {
            stream.writeUTF(citizenship.name());
        }
    }
    @Override
    protected void read(DataInputStream stream)
            throws IOException {
        super.read(stream);
        clientID = stream.readInt();
        hasClubCard = stream.readBoolean();
        satisfactionOfTheService = stream.readInt();
        if (stream.readBoolean()) {
            citizenship =
                    Citizenship.valueOf(stream.readUTF());
        } else {
            citizenship = null;
        }
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
    public List<Order> viewOrderHistory(){
        return new ArrayList<>(orders.values());
    }

    public Order createOrder(OrderType type){
        Order order = Order.createOrder(this,type);
        return order;
    }

    public double calculateSpentMoney(){
        double sum = 0;
        for(Order order : orders.values()){
            sum += order.countOrderValue();
        }
        return sum;
    }
}