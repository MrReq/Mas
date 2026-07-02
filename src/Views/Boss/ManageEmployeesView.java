package Views.Boss;

import Models.Barista;
import Models.Boss;
import Models.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageEmployeesView extends JPanel {

    //=================================================
    // FIELDS
    //=================================================

    private final Boss loggedBoss;

    private JTable employeesTable;

    private DefaultTableModel tableModel;

    private JButton addButton;

    private JButton editButton;

    private JButton removeButton;

    private JButton refreshButton;

    //=================================================
    // CONSTRUCTOR
    //=================================================

    public ManageEmployeesView(Boss boss) {

        this.loggedBoss = boss;

        initializeComponents();

        initializeLayout();

        initializeListeners();

        refreshTable();

    }

    //=================================================
    // COMPONENTS
    //=================================================

    private void initializeComponents() {

        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "First name",
                        "Last name",
                        "Sex",
                        "Role",
                        "Salary",
                        "Favourite CoffeeCountry"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeesTable = new JTable(tableModel);

        employeesTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        employeesTable.getTableHeader().setReorderingAllowed(false);

        addButton = new JButton("Add Employee");

        editButton = new JButton("Edit Employee");

        removeButton = new JButton("Remove Employee");

        refreshButton = new JButton("Refresh");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Employee Management",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        add(new JScrollPane(employeesTable), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(removeButton);
        buttons.add(refreshButton);

        add(buttons, BorderLayout.SOUTH);

    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        refreshButton.addActionListener(e -> refreshTable());

        addButton.addActionListener(e -> addEmployee());

        editButton.addActionListener(e -> editEmployee());

        removeButton.addActionListener(e -> removeEmployee());

    }

    //=================================================
    // TABLE
    //=================================================

    public void refreshTable() {

        tableModel.setRowCount(0);

        for (Employee employee : Employee.getEmployeeExtent()) {

            String favouriteCoffee = "-";

            if (employee instanceof Barista barista) {

                if (barista.getFavouriteCoffeeCountry() != null) {
                    favouriteCoffee = String.valueOf(barista.getFavouriteCoffeeCountry().toString());
                }
            }

            tableModel.addRow(new Object[]{

                    employee.getEmployeeID(),

                    employee.getPersonName(),

                    employee.getPeronSurname(),

                    employee.getPersonSex(),

                    employee.getClass().getSimpleName(),

                    employee.getEmployeeSalary(),

                    favouriteCoffee


            });

        }

    }

    //=================================================
    // ADD
    //=================================================

    private void addEmployee() {
        new AddEmployeeView(this).setVisible(true);
        refreshTable();
    }

    //=================================================
    // EDIT
    //=================================================

    private void editEmployee() {

        int row = employeesTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select employee first."
            );

            return;
        }

        Employee employee =
                Employee.getEmployeeExtent().get(row);

        JOptionPane.showMessageDialog(
                this,
                "Editing employee:\n\n"
                        + employee.getPersonName()
                        + " "
                        + employee.getPeronSurname()
        );

    }

    //=================================================
    // REMOVE
    //=================================================

    private void removeEmployee() {

        int row = employeesTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select employee first."
            );

            return;

        }

        int option = JOptionPane.showConfirmDialog(

                this,

                "Remove selected employee?",

                "Confirmation",

                JOptionPane.YES_NO_OPTION

        );

        if (option != JOptionPane.YES_OPTION) {

            return;

        }

        Employee employee =
                Employee.getEmployeeExtent().get(row);

        Employee.removeEmployee(employee);

        refreshTable();

    }

}