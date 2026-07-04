package Models;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
public class Boss extends Person {
    private static final long serialVersionUID = 1L;
    // FIELDS
    private String password;

    public static LocalTime start = LocalTime.of(8,0);
    public static LocalTime end = LocalTime.of(20,0);
    // CONSTRUCTORS
    public Boss() {
        super();
    }
    public Boss(String name, String surname, String password) {
        super(name, surname);
        this.password = password;
    }
    public Boss(String name, String surname, LocalDate birthDate, Sex sex, String password) {
        super(name, surname, birthDate, sex);
        this.password = password;
    }
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Boss> getBossExtent() {
        return (List<Boss>) (List<?>) ObjectPlus.getExtent(Boss.class);
    }
    // GETTERS
    public String getPassword() {
        return password;
    }
    @Override
    public String getPrivileges() {
        return "ALL";
    }
    // BUSINESS METHODS
    /**
     * Use Case: Manage Products
     */
    public static void manageProducts() {
        Product mostExpensive = null;
        for (Product product : Product.getProductExtent()) {
            if (mostExpensive == null || product.getProductCost() > mostExpensive.getProductCost())
                mostExpensive = product;
        }
        if (mostExpensive != null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Most expensive product:\n\n" +
                            "Name: " + mostExpensive.getProductName() +
                            "\nPrice: " + mostExpensive.getProductCost() + " zł"
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No products available."
            );
        }
    }

    /**
     * Use Case: Manage Employees
     */
    public static void manageEmployees() {
        Employee thBestEmployee = null;
        float salary = 0;
        for(Employee employee : Employee.getEmployeeExtent()){
            if (employee.getEmployeeSalary() > salary)
                thBestEmployee = employee;
        }
        System.out.println("Managing employees...");
        if (thBestEmployee != null) {
            JOptionPane.showMessageDialog(
                    null,
                    "The best Employee:\n\n" + "Name: " + thBestEmployee.getPersonName() + "\nSalary: "
                            + thBestEmployee.getEmployeeSalary() + " zł"
            );
        } else {
            JOptionPane.showMessageDialog(null, "No employees available."
            );
        }
    }

    /**
     * Use Case: Show Income
     */
    public static double countIncome(){
        return Order.getOrderExtent().stream().mapToDouble(Order::countOrderValue).sum();
    }

    public static void showIncome() {
         double sum = Order.getOrderExtent()
                .stream()
                .mapToDouble(Order::countOrderValue)
                .sum();
        if (sum != 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "The income of all Orders is:\n\n" + sum + " zł"
            );
        } else {
            JOptionPane.showMessageDialog(null, "No employees available."
            );
        }

    }
    /**
     * Use Case: Look Order History
     */
    public static void lookOrderHistory() {
        JOptionPane.showMessageDialog(
                null,
                "Order history\n\n" + "Total orders: " + Order.getOrderExtent().size() +
                        "\nCompleted: " + Order.getCompletedOrders().size() +
                        "\nReady: " + Order.getReadyOrders().size() +
                        "\nPreparing: " + Order.getPreparingOrders().size() +
                        "\nNew: " + Order.getNewOrders().size(),
                "Order History",
                JOptionPane.INFORMATION_MESSAGE
        );

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
        return Order.getOrderExtent().size();
    }
    /**
     * Dashboard statistics.
     */
    public int showNumberOfProducts() {
        return Product.getProductExtent().size();
    }

    /**
     * Dashboard statistics.
     */
    public double showAverageOrderValue() {
        if (Order.getOrderExtent().isEmpty())
            return 0;
        return countIncome() / Order.getOrderExtent().size();
    }
    @Override
    public String toString() {
        return "Boss{" + "name='" + personName + '\'' + ", surname='" + peronSurname + '\'' + '}';
    }
}