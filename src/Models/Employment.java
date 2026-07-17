package Models;
import SecondaryClasses.ObjectPlus;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
public class Employment extends ObjectPlus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Boss boss;
    private final Employee employee;
    private final LocalDate employmentDate;
    private LocalDate dismissalDate;
    // EXTENT
    @SuppressWarnings("unchecked")
    public static List<Employment> getEmploymentExtent() {
        return (List<Employment>) (List<?>) ObjectPlus.getExtent(Employment.class);
    }
    public Employment(Boss boss, Employee employee, LocalDate employmentDate) {
        if (boss == null)
            throw new IllegalArgumentException("Boss cannot be null.");
        if (employee == null)
            throw new IllegalArgumentException("Employee cannot be null.");
        if (employmentDate == null)
            throw new IllegalArgumentException("Employment date cannot be null.");
        this.boss = boss;
        this.employee = employee;
        this.employmentDate = employmentDate;
        boss.addEmployment(this);
        employee.addEmployment(this);
    }
    public void dismiss() {
        if (dismissalDate != null) {
            throw new IllegalStateException("Employee has already been dismissed.");
        }
        dismissalDate = LocalDate.now();
    }
    public boolean isActive() {
        return dismissalDate == null;
    }
    public Period getEmploymentPeriod() {
        LocalDate endDate =
                dismissalDate == null
                        ? LocalDate.now()
                        : dismissalDate;

        return Period.between(employmentDate, endDate);
    }
    public String getEmploymentPeriodText() {
        Period period = getEmploymentPeriod();
        return String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
    }
    public Boss getBoss() {
        return boss;
    }
    public Employee getEmployee() {
        return employee;
    }
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }
    public LocalDate getDismissalDate() {
        return dismissalDate;
    }
    public static void showEmployments() {
        StringBuilder sb = new StringBuilder();
        if (getEmploymentExtent().isEmpty()) {
            sb.append("No employments found.");
        } else {
            for (Employment employment : getEmploymentExtent()) {
                sb.append("Boss: ").append(employment.getBoss().getPersonName())
                        .append(" ")
                        .append(employment.getBoss().getPeronSurname())
                        .append("\nEmployee: ")
                        .append(employment.getEmployee().getPersonName())
                        .append(" ")
                        .append(employment.getEmployee().getPeronSurname())
                        .append("\nEmployment date: ")
                        .append(employment.getEmploymentDate())
                        .append("\nDismissal date: ")
                        .append(employment.getDismissalDate() == null
                                ? "-"
                                : employment.getDismissalDate())
                        .append("\nEmployment time: ")
                        .append(employment.getEmploymentPeriodText())
                        .append("\nStatus: ")
                        .append(employment.isActive()
                                ? "ACTIVE"
                                : "DISMISSED")
                        .append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Employments", JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public String toString() {
        return "Employment{" + "boss=" + boss.getPersonName() + " " + boss.getPeronSurname() +
                ", employee=" + employee.getPersonName() + " " + employee.getPeronSurname() + ", employmentDate="
                + employmentDate + ", dismissalDate=" + dismissalDate + '}';}
}