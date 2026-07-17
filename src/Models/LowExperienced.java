package Models;
import Enums.Sex;
import SecondaryClasses.ObjectPlus;
import java.time.LocalDate;
import java.util.List;
public class LowExperienced extends Employee {
    private static final long serialVersionUID = 1L;
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<LowExperienced> getLowExperiencedExtent() {
        return (List<LowExperienced>) (List<?>) ObjectPlus.getExtent(LowExperienced.class);
    }
    public static void showExtent() {
        System.out.println("===== LOW EXPERIENCED EXTENT =====");
        for (LowExperienced employee : getLowExperiencedExtent()) {
            System.out.println(employee);
        }
    }
    // FIELDS
    private float maxBonusValue;
    // CONSTRUCTORS
    public LowExperienced(String name, String surname, LocalDate birthDate, Sex sex, float salary, float maxBonusValue) {
        super(name, surname, birthDate, sex, salary);
        this.maxBonusValue = maxBonusValue;
    }
    public LowExperienced(Employee employee, float maxBonusValue) {
        super(employee.getPersonName(), employee.getPeronSurname(), employee.getPersonDateOfBirth(), employee.getPersonSex(),
                employee.getEmployeeSalary());
        this.maxBonusValue = maxBonusValue;
    }
    public float getMaxBonusValue() {
        return maxBonusValue;
    }
    public void setMaxBonusValue(float maxBonusValue) {
        this.maxBonusValue = maxBonusValue;
    }
    @Override
    public String getPrivileges() {
        return "Maximum bonus: " + maxBonusValue;
    }
    @Override
    public String toString() {
        return super.toString() +
                ", Maximum bonus: " + maxBonusValue;
    }
}