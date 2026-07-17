package Models;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Boss extends Person {
    private static final long serialVersionUID = 1L;
    // FIELDS
    private String password;
    public static LocalTime start = LocalTime.of(8,0);
    public static LocalTime end = LocalTime.of(20,0);
    private  List<Employment> employments = new ArrayList<>();
    //działa zostaje
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (employments == null) {
            employments = new ArrayList<>();
        }
    }
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
    public void addEmployment(Employment employment) {
        System.out.println("Boss.addEmployment()");
        System.out.println(employments);
        if (!employments.contains(employment))
            employments.add(employment);
    }
    public void addEmployee(Employee employee) {
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null.");
        try {new Employment(this, employee, LocalDate.now());}
        catch (Exception e) {throw new RuntimeException(e);}
    }
    public static void manageProducts() {
        Product mostExpensive = null;
        for (Product product : Product.getProductExtent())
            if (mostExpensive == null || product.getProductCost() > mostExpensive.getProductCost()) mostExpensive = product;
        if (mostExpensive != null) {
            JOptionPane.showMessageDialog(null, "Most expensive product:\n\n" +
                    "Name: " + mostExpensive.getProductName() + "\nPrice: " + mostExpensive.getProductCost() + " zł");
        } else {
            JOptionPane.showMessageDialog(null, "No products available."
            );
        }
    }
    public static void manageEmployees() {
        Employee theBestEmployee = null;
        float maxSalary = 0;
        for (Employee employee : Employee.getEmployeeExtent()) {
            if (employee.getEmployeeSalary() > maxSalary) {
                maxSalary = employee.getEmployeeSalary();
                theBestEmployee = employee;
            }
        }
        System.out.println("Managing employees...");
        if (theBestEmployee != null)
            JOptionPane.showMessageDialog(null, "The best employee:\n\n" +
                    "Name: " + theBestEmployee.getPersonName() + " " + theBestEmployee.getPeronSurname() +
                    "\nSalary: " + theBestEmployee.getEmployeeSalary() + " zł");
        else
            JOptionPane.showMessageDialog(null, "No employees available.");

    }
    public static double countIncome(){return Order.getOrderExtent().stream().mapToDouble(Order::countOrderValue).sum();}
    public static void showIncome() {
         double sum = Order.getOrderExtent().stream().mapToDouble(Order::countOrderValue).sum();
        if (sum != 0)
            JOptionPane.showMessageDialog(null, "The income of all Orders is:\n\n" + sum + " zł");
        else
            JOptionPane.showMessageDialog(null, "No employees available.");
    }

    public static void lookOrderHistory() {
        JOptionPane.showMessageDialog(null,
                "Order history\n\n" + "Total orders: " + Order.getOrderExtent().size() +
                        "\nCompleted: " + Order.getCompletedOrders().size() +
                        "\nReady: " + Order.getReadyOrders().size() +
                        "\nPreparing: " + Order.getPreparingOrders().size() +
                        "\nNew: " + Order.getNewOrders().size(),
                "Order History", JOptionPane.INFORMATION_MESSAGE
        );
    }
    public int showNumberOfEmployees() {return Employee.getEmployeeExtent().size();}
    public int showNumberOfClients() {return Client.getClientExtent().size();}
    public int showNumberOfOrders() {return Order.getOrderExtent().size();}
    public int showNumberOfProducts() {return Product.getProductExtent().size();}
    public double showAverageOrderValue() {
        if (Order.getOrderExtent().isEmpty())
            return 0;
        return countIncome() / Order.getOrderExtent().size();
    }
    @Override
    public String toString() {return "Boss{" + "name='" + personName + '\'' + ", surname='" + peronSurname + '\'' + '}';}
    public HighExperienced promote(Employee employee) {
        HighExperienced promoted =
                new HighExperienced(employee, "BMW");
        employee.removeEmployee();
        return promoted;
    }
    public List<Employee> evaluateEmployees() {
        List<Employee> promotedEmployees = new ArrayList<>();
        for (Employee employee : Employee.getEmployeeExtent()) {
            if (employee instanceof Barista barista &&
                    barista.countReadyOrders() >= 5) {
                promote(employee);
                promotedEmployees.add(employee);
            }
        }
        return promotedEmployees;
    }
}