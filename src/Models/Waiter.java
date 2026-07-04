package Models;
import Enums.AllPersonTypes;
import Enums.OrderStatus;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
public class Waiter extends Employee {
    private static final long serialVersionUID = 1L;
    // ATTRIBUTES
    private float waitersTip;
    private int waitersGrade;
    private List<Integer> waitersGrades = new ArrayList<>();
    private final EnumSet<AllPersonTypes> personKind =
            EnumSet.of(AllPersonTypes.Waiter);
    // CONSTRUCTORS
    public Waiter() {
        super();
    }
    public Waiter(String name,
                  String surname,
                  LocalDate birthDate,
                  Sex sex,
                  float salary) {
        super(name, surname, birthDate, sex, salary);
    }
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Waiter> getWaiterExtent() {
        return (List<Waiter>) (List<?>) ObjectPlus.getExtent(Waiter.class);
    }

    // GETTERS

    public float getWaitersTip() {
        return waitersTip;
    }
    public int getWaitersGrade() {
        return waitersGrade;
    }
    public List<Integer> getWaitersGrades() {
        return waitersGrades;
    }
    // SETTERS

    public void setWaitersTip(float waitersTip) {
        this.waitersTip = waitersTip;
    }
    public void addTip(float tip) {
        if (tip > 0) {
            waitersTip += tip;
        }
    }
    public void addGrade(int grade) {
        if (grade < 1 || grade > 5) {
            throw new IllegalArgumentException(
                    "Grade must be between 1 and 5."
            );
        }
        waitersGrades.add(grade);
        waitersGrade = calculateAverageGrade();
    }
    // BUSINESS METHODS
    @Override
    public String getPrivileges() {
        return "WAITER";
    }
    public void serveTable() {
        System.out.println(getPersonName() + " is serving customers.");
    }
    public void serveOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null."
            );
        }
        order.serveOrder();
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
                employeeSalary, waitersGrade, waitersTip
        );
    }
    public void setGrade(int satisfaction) {
        int average = 0;
        for(int a : waitersGrades)
            average += a;
         waitersGrade = average / waitersGrades.size();
    }

//    public int countServedOrders(){
//
//    }
    public void updateOrderStatus(Order order, OrderStatus status){
        order.setStatus(status);
    }
    public void deliverOrder(Order order){
        order.deliver();
    }
}