package Models;

import Enums.AllPersonTypes;
import Enums.Level;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class Employee extends Person {
    //EXTENT SESSION
    /**
     * Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addEmployee" which adds instance of {@linkplain Employee} to extent collection</br>
     * <br>Private Static method "removeEmployee" which removes instance of {@linkplain Employee} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Employee} line by line.</br>
     */
    @Override
    public String toString() {
        return "Employee: " + personName + " salary : " + employeeSalary;
    }
    private static List<Employee> extent = new ArrayList<>();
    private static void addEmployee(Employee employee) {
        extent.add(employee);
    }
    public static void removeEmployee(Employee employee){
        ObjectPlus.getExtent(Employee.class).remove(employee);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Employee.class.getName());
        for (Employee employee : extent) {
            System.out.println(employee);
        }
    }
    // SERIALIZATION (WRITE)
    protected void write(DataOutputStream stream) throws IOException {
        super.write(stream);
        stream.writeInt(employeeID);
        stream.writeFloat(experienceInCoffeeHouse);
        stream.writeFloat(employeeSalary);
        stream.writeInt(previousEmployeeExperience.size());
        stream.writeInt(previousEmployeeExperience.size());
        for (String c : previousEmployeeExperience) {
            stream.writeUTF(c);
        }
        stream.writeUTF(level != null ? level.toString() : "");
    }
    // SERIALIZATION (READ)
    protected void read(DataInputStream stream) throws IOException {
        super.read(stream);
        employeeID = stream.read();
        experienceInCoffeeHouse = stream.read();
        employeeSalary = stream.read();
        int size = stream.readInt();
        previousEmployeeExperience = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String experience = stream.readUTF();
            previousEmployeeExperience.add(experience);
        }
    }
    //EXTENT SESSION END
    //FIELDS SESSION START
    private static int staticEmployeeIDcounter = 1;
    /**
     * Simple, Single, Required, Object, Concrete Attribute "employeeID" typed {@linkplain Integer}
     */
    int employeeID;
    /**
     * Simple, Single, Required, Object, Concrete Attribute "experienceInCoffeeHouse" typed {@linkplain Integer}
     */
    float experienceInCoffeeHouse;
    /**
     * Simple, Single, Required, Object, Concrete Attribute "employeeSalary" typed {@linkplain Float}
     */
    float employeeSalary;
    /**
     * Complex, Repeatable, Required, Object, Concrete Attribute "previousExperience" typed {@linkplain List<String>}
     */
    List<String> previousEmployeeExperience;
    /**
     * Simple, Single, Required, Object, Concrete Attribute "level" typed {@linkplain Level}
     */
    Level level;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Employee(String name, String surname, LocalDate dateOfBirth, Sex sex, float salary) {
        super(name, surname, dateOfBirth, sex);
        employeeSalary = salary;
        this.employeeID = staticEmployeeIDcounter;
        staticEmployeeIDcounter++;
        addEmployee(this);
    }
    public Employee() {
        super();
    }

    @Override
    public String getPrivileges() {
        return null;
    }

    public float getEmployeeSalary() {
        return employeeSalary;
    }
    public String getName() {
        return super.personName;
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END

    public int getEmployeeID() {
        return employeeID;
    }

    //METHODS SESSION START
    //STATIC METHODS
    public static List<Employee> findEmployeesWithHigherSalaryThan(float minSalary) {
        return extent.stream()
                .filter(emp -> emp.getEmployeeSalary() > minSalary)
                .collect(Collectors.toList());
    }
    public static List<Employee> findEmployeesWithLowerSalaryThan(float maxSalary) {
        return extent.stream()
                .filter(emp -> emp.getEmployeeSalary() < maxSalary)
                .collect(Collectors.toList());
    }
    public static List<Employee> findEmployeesWithLowerSalaryThan(float maxSalary, char firstLetter) {
        return extent.stream()
                .filter(emp -> emp.personName.startsWith(String.valueOf(firstLetter)))
                .filter(emp -> emp.getEmployeeSalary() < maxSalary)
                .collect(Collectors.toList());
    }
    public static Employee findEmployeesWithTheLowestSalary() {
        return extent.stream()
                .min(Comparator.comparing(Employee::getEmployeeSalary))
                .orElse(null);
    }
    public static Employee findEmployeesWithTheHighestSalary() {
        return extent.stream()
                .max(Comparator.comparing(Employee::getEmployeeSalary))
                .orElse(null);
    }
    //METHODS SESSION END
    //BINARY ASSOCIATION
    public ArrayList<CoffeeHouse> coffeeHousesWhereIsHired = new ArrayList<>();
    public int[] coffeeHouseIDs;
    private static List<Employee> extentForBinaryAssociation = new ArrayList<>();
    public Employee(int employeeID, String name, String surname, LocalDate dateOfBirth, Sex sex, float salary, int[] coffeeHouseIDs) {
        super(name, surname, dateOfBirth, sex);
        extentForBinaryAssociation.add(this);
        employeeSalary = salary;
        this.employeeID = employeeID;
        staticEmployeeIDcounter++;
        addEmployee(this);
        this.coffeeHouseIDs = coffeeHouseIDs;
    }
    public static Employee findEmployee(int id) throws Exception {
        for (Employee employee : extentForBinaryAssociation) {
            if (employee.employeeID == id) {
                return employee;
            }
        }
        throw new Exception("Unable to find an employee with the id = " + id);
    }
    @SuppressWarnings("unchecked")
    public static List<Employee> getEmployeeExtent() {

        List<Employee> result = new ArrayList<>();

        result.addAll((List<Employee>)(List<?>)ObjectPlus.getExtent(Employee.class));
        result.addAll((List<Employee>)(List<?>)ObjectPlus.getExtent(Barista.class));
        result.addAll((List<Employee>)(List<?>)ObjectPlus.getExtent(Waiter.class));
        result.addAll((List<Employee>)(List<?>)ObjectPlus.getExtent(Cleaner.class));

        return result;
    }
    private List<CoffeeHouse> coffeeHouses = new ArrayList<>();
    public void addCoffeeHouse(CoffeeHouse coffeeHouse) {
        if (!coffeeHousesWhereIsHired.contains(coffeeHouse)) {
            coffeeHousesWhereIsHired.add(coffeeHouse);
            coffeeHouse.addEmployee(this);
        }
    }
    /**Recursive association
     */
    //RECURSIVE ASSOCIATION
    private Employee recommender;
    private List<Employee> recommendedEmployees = new ArrayList<>();
    public void setRecommender(Employee newRecommender) {
        if (this.recommender == newRecommender) {
            return;
        }
        if (this.recommender != null) {
            this.recommender.recommendedEmployees.remove(this);
        }
        this.recommender = newRecommender;
        // dodaj po drugiej stronie
        if (newRecommender != null &&
                !newRecommender.recommendedEmployees.contains(this)) {
            newRecommender.recommendedEmployees.add(this);
        }
    }
    public void addRecommendedEmployee(Employee employee) {
        if (employee == null) return;
        employee.setRecommender(this);
    }
    public List<Employee> getRecommendedEmployees(){
        return recommendedEmployees;
    }
    /**ASSOCIATION WITH ATTRIBUTE
     */
    private List<Employment_CoffeeHouse_Employee> employments = new ArrayList<>();
    public void addEmployment(Employment_CoffeeHouse_Employee employment) {
        if (!employments.contains(employment)) {
            employments.add(employment);
        }
    }
    public List<Employment_CoffeeHouse_Employee> getEmployments() {
        return employments;
    }
    public Employee getRecommendedBy(){
        return recommender;
    }
    /**Removing connections
     */
    public void removeeCoffeeHouse(CoffeeHouse coffeeHouseToRemove) {
        if(coffeeHouses.contains(coffeeHouseToRemove)) {
            coffeeHouses.remove(coffeeHouseToRemove);
            coffeeHouseToRemove.removeEmployee(this);
        }
    }
    //OVERLAPPING
    private final EnumSet<AllPersonTypes> personKind =  EnumSet.of(AllPersonTypes.Employee);


}