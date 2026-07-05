package Views.Cleaner;
import Models.Cleaner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import Models.Order;
import Enums.OrderStatus;
public class CleanerTasksPanel extends JPanel {
    private final Cleaner cleaner;
    private JTable tasksTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton completeTaskButton;
    private JButton detailsButton;
    private CleanerDashboardView parent;
    public CleanerTasksPanel(Cleaner cleaner,CleanerDashboardView parent) {
        this.cleaner = cleaner;
        this.parent = parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Task ID", "Client name", "Value", "Status"});
        tasksTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tasksTable.setRowSorter(sorter);
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshButton = new JButton("Refresh");
        completeTaskButton = new JButton("Mark as completed");
        detailsButton = new JButton("Details");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        bottomPanel.add(detailsButton);
        bottomPanel.add(completeTaskButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        detailsButton.addActionListener(e -> showDetails());
        completeTaskButton.addActionListener(e -> completeTask());
    }
    // TABLE
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if (order.getOrderStatus() != OrderStatus.PAID)
                continue;
            String client = "-";
            if (order.getClient() != null) {
                client = order.getClient().getPersonName() + " " + order.getClient().getPeronSurname();
            }
            tableModel.addRow(new Object[]{order.getOrderID(), client, order.countOrderValue(), order.getOrderStatus()
            });
        }
    }
    // DETAILS
    private void showDetails() {
        int row = tasksTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select a task first.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Task details will appear here.");
    }
    // COMPLETE TASK
    private void completeTask() {
        int row = tasksTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a task first.");
            return;
        }
        int modelRow = tasksTable.convertRowIndexToModel(row);
        int orderId = (Integer) tableModel.getValueAt(modelRow, 0);
        Order order = Order.findById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order not found.");
            return;
        }
        order.setOrderStatus(OrderStatus.FINISHED);
        JOptionPane.showMessageDialog(this, "Cleaning completed successfully.", "Completed",
                JOptionPane.INFORMATION_MESSAGE
        );
        parent.refreshAllPanels();
    }
    public void reload(){
        refreshTable();
    }
}