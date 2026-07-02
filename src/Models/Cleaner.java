package Models;

import Enums.Sex;
import Enums.Shift;
import SecondaryClasses.ObjectPlus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cleaner extends Employee {
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addCleaner" which adds instance of {@linkplain Cleaner} to extent collection</br>
     * <br>Private Static method "removeCleaner" which removes instance of {@linkplain Cleaner} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Cleaner} line by line.</br>
     */
    @Override
    public String toString() {
        return "Cleaner: " + personName + ", id: " + super.toString();
    }
    private static List<Cleaner> extent = new ArrayList<>();
    private static void addCleaner(Cleaner cleaner) {
        extent.add(cleaner);
    }
    private static void removeCleaner(Cleaner cleaner) {
        extent.remove(cleaner);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Cleaner.class.getName());
        for (Cleaner cleaner : extent) {
            System.out.println(cleaner);
        }
    }
    @SuppressWarnings("unchecked")
    public static List<Cleaner> getCleanerExtent() {
        return (List<Cleaner>) (List<?>) ObjectPlus.getExtent(Cleaner.class);
    }
    //EXTENT SESSION END
    //FIELDS SESSION START
    /**Complex, Single, Required, Object, Concrete Attribute "shift" typed {@linkplain Shift}
     */
    Shift shift;
    /**Simple, Single, Required, Object, Concrete Attribute "assignedArea" typed {@linkplain String}
     */
    String assignedArea;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Cleaner(String name, String surname, LocalDate dateOfBirth, Sex sex, Shift shift, String assignedArea, float salary) {
        super(name, surname, dateOfBirth, sex, salary);
        this.shift = shift;
        this.assignedArea = assignedArea;
    }
    public static void setExtent(List<Cleaner> extent) {
        Cleaner.extent = extent;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getAssignedArea() {
        return assignedArea;
    }

    public void setAssignedArea(String assignedArea) {
        this.assignedArea = assignedArea;
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END


    //METHODS SESSION START
    //METHODS SESSION END
}