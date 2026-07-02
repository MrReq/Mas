package Models;

import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.time.LocalDate;
import java.util.List;

public class Boss extends Person {

    //=================================================
    // FIELDS
    //=================================================

    private String password;

    //=================================================
    // CONSTRUCTORS
    //=================================================

    public Boss() {

        super();

    }

    public Boss(String name,
                String surname,
                String password) {

        super(name, surname);

        this.password = password;

    }

    public Boss(String name,
                String surname,
                LocalDate birthDate,
                Sex sex,
                String password) {

        super(name, surname, birthDate, sex);

        this.password = password;

    }

    //=================================================
    // EXTENT
    //=================================================

    @SuppressWarnings("unchecked")
    public static List<Boss> getBossExtent() {

        return (List<Boss>) (List<?>) ObjectPlus.getExtent(Boss.class);

    }

    //=================================================
    // GETTERS
    //=================================================

    public String getPassword() {

        return password;

    }

    @Override
    public String getPrivileges() {

        return "ALL";

    }

    //=================================================
    // BUSINESS METHODS
    //=================================================

    /**
     * Use Case: Manage Products
     */
    public void manageProducts() {

        System.out.println("Managing products...");

    }

    /**
     * Use Case: Manage Employees
     */
    public void manageEmployees() {

        System.out.println("Managing employees...");

    }

    /**
     * Use Case: Show Income
     */
    public double showIncome() {

        return Order.getExtent()

                .stream()

                .mapToDouble(Order::countOrderValue)

                .sum();

    }

    /**
     * Use Case: Look Order History
     */
    public List<Order> lookOrderHistory() {

        return Order.getExtent();

    }

    /**
     * Dashboard statistics.
     */
    public int showNumberOfEmployees() {

        return Employee.getExtent().size();

    }

    /**
     * Dashboard statistics.
     */
    public int showNumberOfClients() {

        return Client.getClientExtent().size();

    }

    /**
     * Dashboard statistics.
     */
    public int showNumberOfOrders() {

        return Order.getExtent().size();

    }

    /**
     * Dashboard statistics.
     */
    public int showNumberOfProducts() {

        return Product.getExtent().size();

    }

    /**
     * Dashboard statistics.
     */
    public double showAverageOrderValue() {

        if (Order.getExtent().isEmpty()) {

            return 0;

        }

        return showIncome() / Order.getExtent().size();

    }

    @Override
    public String toString() {

        return "Boss{" +

                "name='" + personName + '\'' +

                ", surname='" + peronSurname + '\'' +

                '}';

    }

}