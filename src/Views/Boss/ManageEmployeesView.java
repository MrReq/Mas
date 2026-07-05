package Views.Boss;
import Models.Barista;
import Models.Boss;
import Models.Employee;
import Models.Employment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
public class ManageEmployeesView extends JPanel {
    // FIELDS
    private final Boss loggedBoss;
    private JTable employeesTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JButton manageEmployeesButton;
    private JButton EmploymentsButton;
    private JButton PromoteEmployeeButton;
    private JButton showNumberOfEmployees;
    private JButton showNumberOfClients;

    // CONSTRUCTOR
    public ManageEmployeesView(Boss loggedBoss) {
        this.loggedBoss = loggedBoss;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "First name", "Last name", "Sex", "Role", "Salary", "Favourite CoffeeCountry"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeesTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        employeesTable.setRowSorter(sorter);
        employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeesTable.getTableHeader().setReorderingAllowed(false);
        addButton = new JButton("Add Employee");
        editButton = new JButton("Edit Employee");
        removeButton = new JButton("Remove Employee");
        refreshButton = new JButton("Refresh");
        manageEmployeesButton = new JButton("Manage Employees");
        EmploymentsButton = new JButton("Show All Emplyments");
        PromoteEmployeeButton = new JButton("Show all promoted employees");
        showNumberOfEmployees = new JButton("Show Number of Employees");
        showNumberOfClients = new JButton("Show Number of Clients");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout(10, 10));
        JLabel title = new JLabel("Employee Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(employeesTable), BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(removeButton);
        buttons.add(refreshButton);
        buttons.add(manageEmployeesButton);
        buttons.add(EmploymentsButton);
        buttons.add(PromoteEmployeeButton);
        buttons.add(showNumberOfEmployees);
        buttons.add(showNumberOfClients);
        add(buttons, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        addButton.addActionListener(e -> addEmployee());
        editButton.addActionListener(e -> editEmployee());
        removeButton.addActionListener(e -> removeEmployee());
        manageEmployeesButton.addActionListener(e -> Boss.manageEmployees());
        EmploymentsButton.addActionListener(e -> Employment.showEmployments());
        PromoteEmployeeButton.addActionListener(e -> showPromotedEmployees());
        showNumberOfEmployees.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Number of Employees:\n\n" + loggedBoss.showNumberOfEmployees());});
        showNumberOfClients.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Number of Clients:\n\n" + loggedBoss.showNumberOfClients());});

    }
    // TABLE
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Employee employee : Employee.getEmployeeExtent()) {
            String favouriteCoffee = "-";
            if (employee instanceof Barista barista)
                if (barista.getFavouriteCoffeeCountry() != null)
                    favouriteCoffee = String.valueOf(barista.getFavouriteCoffeeCountry().toString());
            tableModel.addRow(new Object[]{employee.getEmployeeID(), employee.getPersonName(), employee.getPeronSurname(),
                    employee.getPersonSex(), employee.getClass().getSimpleName(), employee.getEmployeeSalary(), favouriteCoffee
            });
        }
    }
    // ADD
    private void addEmployee() {
        new AddEmployeeView(this,loggedBoss).setVisible(true);
        refreshTable();
    }
    // EDIT
    private void editEmployee() {
        int row = employeesTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select employee first.");
            return;
        }
        Employee employee = Employee.getEmployeeExtent().get(row);
        JOptionPane.showMessageDialog(this, "Editing employee:\n\n" + employee.getPersonName() +
                        " " + employee.getPeronSurname());
    }
    // REMOVE
    private void removeEmployee() {
        int row = employeesTable.getSelectedRow();
        if (row == -1) {JOptionPane.showMessageDialog(this, "Select employee.");
            return;
        }
        int employeeId = (Integer) tableModel.getValueAt(row, 0);
        Employee employee = Employee.findById(employeeId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Delete selected employee?", "Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (option != JOptionPane.YES_OPTION) {
            return;
        }
        employee.removeEmployee();
        refreshTable();
        JOptionPane.showMessageDialog(this, "Employee removed.");
    }

    public void showPromotedEmployees(){
        List<Employee> promoted = loggedBoss.evaluateEmployees();
        if (promoted.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employee met the promotion requirements.", "Promotion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The following employees have been promoted:\n\n");
        for (Employee employee : promoted) {
            sb.append("• ")
                .append(employee.getPersonName())
                .append(" ")
                .append(employee.getPeronSurname())
                .append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Promotion completed", JOptionPane.INFORMATION_MESSAGE);
    }
}