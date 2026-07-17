package Models;
import Enums.Sex;
import Enums.Shift;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.List;
public class Cleaner extends Employee {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Cleaner: " + personName + ", id: " + super.toString();
    }
    @SuppressWarnings("unchecked")
    public static List<Cleaner> getCleanerExtent() {
        return (List<Cleaner>) (List<?>) ObjectPlus.getExtent(Cleaner.class);
    }
    Shift shift;
    String assignedArea;
    //CONSTRUCTORS
    public Cleaner(String name, String surname, LocalDate dateOfBirth, Sex sex, Shift shift, String assignedArea, float salary) {
        super(name, surname, dateOfBirth, sex, salary);
        this.shift = shift;
        this.assignedArea = assignedArea;
    }
    public Shift getShift() {
        return shift;
    }
    public String getAssignedArea() {
        return assignedArea;
    }
    public void setAssignedArea(String assignedArea) {
        this.assignedArea = assignedArea;
    }
    public void setShift(Shift shift) {
        this.shift = shift;
    }
}