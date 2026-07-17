package Models;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.List;
public class HighExperienced extends Employee {
    private static final long serialVersionUID = 1L;
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<HighExperienced> getHighExperiencedExtent() {
        return (List<HighExperienced>) (List<?>) ObjectPlus.getExtent(HighExperienced.class);
    }
    public static void showExtent() {
        System.out.println("===== HIGH EXPERIENCED EXTENT =====");
        for (HighExperienced employee : getHighExperiencedExtent())
            System.out.println(employee);
    }
    private String companyCar;
    // CONSTRUCTORS
    public HighExperienced(String name, String surname, LocalDate birthDate, Sex sex, float salary) {
        super(name, surname, birthDate, sex, salary);
    }
    public HighExperienced(Employee employee,
                           String companyCar) {
        super(employee.getPersonName(), employee.getPeronSurname(), employee.getPersonDateOfBirth(), employee.getPersonSex(),
                employee.getEmployeeSalary()
        );
        this.companyCar = companyCar;
    }
    // GETTERS / SETTERS
    public String getCompanyCar() {
        return companyCar;
    }
    public void setCompanyCar(String companyCar) {
        this.companyCar = companyCar;
    }
    @Override
    public String getPrivileges() {
        return "Company car: " + companyCar;
    }
    // TO STRING
    @Override
    public String toString() {
        return super.toString() +
                ", Company car: " + companyCar;
    }
}