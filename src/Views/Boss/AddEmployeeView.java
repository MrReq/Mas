package Views.Boss;
import Enums.CoffeeCountry;
import Enums.Sex;
import Enums.Shift;
import Models.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
public class AddEmployeeView extends JFrame {
    private final Boss loggedBoss;
    private final ManageEmployeesView parent;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField salaryField;

    private JComboBox<Sex> sexComboBox;
    private JComboBox<CoffeeCountry> coffeeCountryComboBox;
    private JComboBox<Shift> shiftComboBox;
    private JComboBox<String> assignedAreaComboBox;
    private JComboBox<String> roleComboBox;

    private JLabel coffeeCountryLabel;
    private JLabel shiftLabel;
    private JLabel assignedAreaLabel;

    private JSpinner birthDateSpinner;

    private JButton createButton;
    private JButton cancelButton;

    public AddEmployeeView(ManageEmployeesView parent, Boss loggedBoss) {

        this.parent = parent;
        this.loggedBoss = loggedBoss;

        initializeFrame();
        initializeComponents();
        initializeLayout();
        initializeListeners();
        updateFields();
    }
    private void initializeFrame() {
        setTitle("Add Employee");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void initializeComponents() {
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        salaryField = new JTextField();
        sexComboBox = new JComboBox<>(Sex.values());
        roleComboBox = new JComboBox<>();
        roleComboBox.addItem("Barista");
        roleComboBox.addItem("Waiter");
        roleComboBox.addItem("Cleaner");
        coffeeCountryComboBox = new JComboBox<>(CoffeeCountry.values());
        shiftComboBox = new JComboBox<>(Shift.values());
        assignedAreaComboBox = new JComboBox<>();
        assignedAreaComboBox.addItem("Kitchen");
        assignedAreaComboBox.addItem("Main Hall");
        assignedAreaComboBox.addItem("Terrace");
        assignedAreaComboBox.addItem("Garden");
        assignedAreaComboBox.addItem("Storage");
        assignedAreaComboBox.addItem("Toilets");
        coffeeCountryLabel = new JLabel("Coffee country:");
        shiftLabel = new JLabel("Shift:");
        assignedAreaLabel = new JLabel("Assigned area:");
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "dd.MM.yyyy"));
        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");
    }
    private void initializeLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("First name:"), gbc);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Last name:"), gbc);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        panel.add(salaryField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Birth date:"), gbc);
        gbc.gridx = 1;
        panel.add(birthDateSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        panel.add(sexComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(coffeeCountryLabel, gbc);
        gbc.gridx = 1;
        panel.add(coffeeCountryComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(shiftLabel, gbc);
        gbc.gridx = 1;
        panel.add(shiftComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(assignedAreaLabel, gbc);
        gbc.gridx = 1;
        panel.add(assignedAreaComboBox, gbc);
        JPanel buttons = new JPanel();
        buttons.add(createButton);
        buttons.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);
        add(panel);
    }

    private void initializeListeners() {
        createButton.addActionListener(e -> createEmployee());
        cancelButton.addActionListener(e -> dispose());
        roleComboBox.addActionListener(e -> updateFields());
    }
    // SHOW / HIDE FIELDS
    private void updateFields() {
        String role = (String) roleComboBox.getSelectedItem();
        boolean isBarista = "Barista".equals(role);
        boolean isCleaner = "Cleaner".equals(role);
        coffeeCountryLabel.setVisible(isBarista);
        coffeeCountryComboBox.setVisible(isBarista);
        shiftLabel.setVisible(isCleaner);
        shiftComboBox.setVisible(isCleaner);
        assignedAreaLabel.setVisible(isCleaner);
        assignedAreaComboBox.setVisible(isCleaner);
        revalidate();
        repaint();
    }
    // CREATE EMPLOYEE
    private void createEmployee() {
        if(Employee.getEmployeeExtent().size() < 9){
            try {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                if (firstName.isBlank() || lastName.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Fill all required fields.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                float salary = Float.parseFloat(salaryField.getText());
                Date date = (Date) birthDateSpinner.getValue();
                LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Sex sex = (Sex) sexComboBox.getSelectedItem();
                String role = (String) roleComboBox.getSelectedItem();
                // DUPLICATE CHECK
                for (Employee employee : Employee.getEmployeeExtent()) {
                    if (employee.getPersonName().equalsIgnoreCase(firstName)
                            &&
                            employee.getPeronSurname().equalsIgnoreCase(lastName)) {
                        JOptionPane.showMessageDialog(this, "Employee already exists.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                // CREATE EMPLOYEE
                switch (role) {
                    case "Barista" -> {
                        CoffeeCountry coffeeCountry = (CoffeeCountry) coffeeCountryComboBox.getSelectedItem();
                        Employee b = new Barista(firstName, lastName, birthDate, sex, salary, coffeeCountry);
                        loggedBoss.addEmployee(b);
                    }
                    case "Waiter" ->{
                        Employee w = new Waiter(firstName, lastName, birthDate, sex, salary);
                        loggedBoss.addEmployee(w);
                    }
                    case "Cleaner" -> {
                        Shift shift = (Shift) shiftComboBox.getSelectedItem();
                        String assignedArea = (String) assignedAreaComboBox.getSelectedItem();
                        Employee c = new  Cleaner(firstName, lastName, birthDate, sex, shift, assignedArea, salary);
                        loggedBoss.addEmployee(c);
                    }
                }
                JOptionPane.showMessageDialog(this,
                        "Employee created successfully.");
                System.out.println(Employee.getEmployeeExtent().size());
                System.out.println(Employee.findEmployeesWithHigherSalaryThan(0).size());
                parent.refreshTable();
                dispose();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Salary must be a valid number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(this,"You can hire maximum 9 Employees");
        }
    }
}