package Models;
import Enums.AllPersonTypes;
import Enums.Citizenship;
import Enums.Level;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
public class Employee extends Person {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Employee: " + personName + " salary : " + employeeSalary;
    }

    public void removeEmployee() {
        removeFromExtent();
    }
    public static List<Boss> getBossExtent() {
        return (List<Boss>) (List<?>) ObjectPlus.getExtent(Boss.class);
    }
    private static int staticEmployeeIDcounter = 1;
    private final List<Employment> employments = new ArrayList<>();
    int employeeID;
    float experienceInCoffeeHouse;
    float employeeSalary;
    List<String> previousEmployeeExperience;
    Level level;
    public Employee(String name, String surname, LocalDate dateOfBirth, Sex sex, float salary) {
        super(name, surname, dateOfBirth, sex);
        employeeSalary = salary;
        this.employeeID = staticEmployeeIDcounter;
        staticEmployeeIDcounter++;
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
    public int getEmployeeID() {
        return employeeID;
    }
    public static List<Employee> findEmployeesWithHigherSalaryThan(float minSalary) {
        return getEmployeeExtent().stream()
                .filter(emp -> emp.getEmployeeSalary() > minSalary)
                .collect(Collectors.toList());
    }
    public static List<Employee> findEmployeesWithLowerSalaryThan(float maxSalary) {
        return getEmployeeExtent().stream()
                .filter(emp -> emp.getEmployeeSalary() < maxSalary)
                .collect(Collectors.toList());
    }
    public static List<Employee> findEmployeesWithLowerSalaryThan(float maxSalary, char firstLetter) {
        return getEmployeeExtent().stream()
                .filter(emp -> emp.personName.startsWith(String.valueOf(firstLetter)))
                .filter(emp -> emp.getEmployeeSalary() < maxSalary)
                .collect(Collectors.toList());
    }
    public static Employee findEmployeesWithTheLowestSalary() {
        return getEmployeeExtent().stream()
                .min(Comparator.comparing(Employee::getEmployeeSalary))
                .orElse(null);
    }
    public static Employee findEmployeesWithTheHighestSalary() {
        return getEmployeeExtent().stream()
                .max(Comparator.comparing(Employee::getEmployeeSalary))
                .orElse(null);
    }
    public int[] coffeeHouseIDs;
    private static List<Employee> extentForBinaryAssociation = new ArrayList<>();
    public Employee(int employeeID, String name, String surname, LocalDate dateOfBirth, Sex sex, float salary, int[] coffeeHouseIDs) {
        super(name, surname, dateOfBirth, sex);
        extentForBinaryAssociation.add(this);
        employeeSalary = salary;
        this.employeeID = employeeID;
        staticEmployeeIDcounter++;
        this.coffeeHouseIDs = coffeeHouseIDs;
    }
    public static Employee findEmployee(int id) throws Exception {
        for (Employee employee : extentForBinaryAssociation) {
            if (employee.employeeID == id)
                return employee;
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
    public void addEmployment(Employment employment) {
        System.out.println("Employee.addEmployment()");
        System.out.println(employments);
        if (!employments.contains(employment)) {
            employments.add(employment);
        }
    }
    public Employment getCurrentEmployment() {
        return Employment.getEmploymentExtent()
                .stream()
                .filter(e -> e.getEmployee() == this)
                .filter(Employment::isActive)
                .findFirst()
                .orElse(null);
    }
    public Employee getRecommendedBy(){
        return recommender;
    }
    public static Employee findById(int id) {
        for (Employee employee : getEmployeeExtent()) {
            if (employee.getEmployeeID() == id)
                return employee;
        }
        return null;
    }
    public static void employeerebuildCounter() {
        int maxId = 0;
        for (Employee employee : getEmployeeExtent()) {
            if (employee.getEmployeeID() > maxId)
                maxId = employee.getEmployeeID();
        }
        staticEmployeeIDcounter = maxId + 1;
    }
    private final EnumSet<AllPersonTypes> personKind =  EnumSet.of(AllPersonTypes.Employee);
    public Client becomeClient(boolean hasClubCard, Citizenship citizenship) {
        return Client.createFromEmployee(this, hasClubCard, citizenship);
    }
}