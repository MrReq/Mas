package Models;

import Enums.AllPersonTypes;
import Enums.OrderStatus;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Waiter extends Employee {

    //====================================================
    // ATTRIBUTES
    //====================================================

    /**
     * Total tips earned.
     */
    private float waitersTip;

    /**
     * Average grade.
     */
    private int waitersGrade;

    /**
     * List of all received grades.
     */
    private List<Integer> waitersGrades = new ArrayList<>();

    /**
     * Overlapping example.
     */
    private final EnumSet<AllPersonTypes> personKind =
            EnumSet.of(AllPersonTypes.Waiter);

    //====================================================
    // CONSTRUCTORS
    //====================================================

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

    //====================================================
    // EXTENT
    //====================================================

    @SuppressWarnings("unchecked")
    public static List<Waiter> getWaiterExtent() {

        return (List<Waiter>) (List<?>) ObjectPlus.getExtent(Waiter.class);

    }

    //====================================================
    // GETTERS
    //====================================================

    public float getWaitersTip() {

        return waitersTip;

    }

    public int getWaitersGrade() {

        return waitersGrade;

    }

    public List<Integer> getWaitersGrades() {

        return waitersGrades;

    }

    //====================================================
    // SETTERS
    //====================================================

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

    //====================================================
    // BUSINESS METHODS
    //====================================================

    @Override
    public String getPrivileges() {

        return "WAITER";

    }

    public void serveTable() {

        System.out.println(

                getPersonName()
                        + " is serving customers."

        );

    }

    private int calculateAverageGrade() {

        if (waitersGrades.isEmpty()) {

            return 0;

        }

        int sum = 0;

        for (Integer grade : waitersGrades) {

            sum += grade;

        }

        return Math.round((float) sum / waitersGrades.size());

    }

    //====================================================
    // SERIALIZATION
    //====================================================

    @Override
    protected void write(DataOutputStream stream)
            throws IOException {

        super.write(stream);

        stream.writeFloat(waitersTip);

        stream.writeInt(waitersGrade);

        stream.writeInt(waitersGrades.size());

        for (Integer grade : waitersGrades) {

            stream.writeInt(grade);

        }

    }

    @Override
    protected void read(DataInputStream stream)
            throws IOException {

        super.read(stream);

        waitersTip = stream.readFloat();

        waitersGrade = stream.readInt();

        int size = stream.readInt();

        waitersGrades = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            waitersGrades.add(stream.readInt());

        }

    }

    //====================================================
    // OBJECT METHODS
    //====================================================

    @Override
    public String toString() {

        return String.format(

                "Waiter{id=%d, name='%s %s', salary=%.2f, grade=%d, tips=%.2f}",

                employeeID,

                personName,

                peronSurname,

                employeeSalary,

                waitersGrade,

                waitersTip

        );

    }

    public void setGrade(int satisfaction) {
        int average = 0;
        for(int a : waitersGrades){
            average += a;
        }
         waitersGrade = average / waitersGrades.size();
    }

    public void updateOrderStatus(Order order,
                                  OrderStatus status){
        order.setStatus(status);
    }

    public void deliverOrder(Order order){
        order.deliver();
    }
}