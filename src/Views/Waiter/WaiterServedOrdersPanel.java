package Views.Waiter;
import Enums.OrderStatus;
import Models.Order;
import Models.Delivery;
import Models.Product;
import Models.Waiter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableRowSorter;
public class WaiterServedOrdersPanel extends JPanel {
    private Waiter loggedWaiter;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton showAllDeliveriesButton;
    private JButton showOnlyMyDeliveriesButton;
    private JButton receivePaymentButton;
    private WaiterDashboardView parent;

    public WaiterServedOrdersPanel(Waiter loggedWaiter, WaiterDashboardView parent) {
        this.loggedWaiter = loggedWaiter;
        this.parent=parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Order ID", "Client", "Products", "Value", "Status"});
        ordersTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        ordersTable.setRowSorter(sorter);
        refreshButton = new JButton("Refresh");
        showAllDeliveriesButton = new JButton("Show All Deliveries");
        showOnlyMyDeliveriesButton = new JButton("Show Only My Deliveries");
        receivePaymentButton = new JButton("Receive Payment");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Served Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(showAllDeliveriesButton);
        bottom.add(showOnlyMyDeliveriesButton);
        bottom.add(receivePaymentButton);
        add(bottom, BorderLayout.SOUTH);
    }
    // LISTENERS

    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        showAllDeliveriesButton.addActionListener(e -> showAllDeliveries());
        showOnlyMyDeliveriesButton.addActionListener(e -> showOnlyMyDeliveries());
        receivePaymentButton.addActionListener(e -> receivePayment());
    }
    // TABLE
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if (order.getOrderStatus() != OrderStatus.SERVED)
                continue;
            StringBuilder products = new StringBuilder();
            for (Product product : order.getProducts()) {
                if (products.length() > 0)
                    products.append(", ");
                products.append(product.getProductName());
            }
            String client = "-";
            if (order.getClient() != null) {
                client = order.getClient().getPersonName() + " " + order.getClient().getPeronSurname();
            }
            tableModel.addRow(new Object[]{order.getOrderID(), client, products.toString(), order.countOrderValue(),
            order.getOrderStatus()});
        }
    }
    private void receivePayment() {
        int row = ordersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order.");
            return;
        }
        int orderId = (Integer) tableModel.getValueAt(row, 0);
        Order order = Order.findById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order not found.");
            return;
        }
        loggedWaiter.receivePayment(order);
        JOptionPane.showMessageDialog(this, "Payment has been received.");
        parent.refreshAllPanels();
    }
    public void reload() {
        refreshTable();
    }
    private void showAllDeliveries() {
        StringBuilder sb = new StringBuilder();
        if (Delivery.getDeliveryExtent().isEmpty()) {
            sb.append("No deliveries.");
        } else {
            for (Delivery delivery : Delivery.getDeliveryExtent()) {
                sb.append("Delivery ID: ")
                        .append(delivery.getDeliveryID())
                        .append("\nOrder ID: ")
                        .append(delivery.getOrder().getOrderID())
                        .append("\nWaiter: ")
                        .append(delivery.getWaiter().getPersonName())
                        .append(" ")
                        .append(delivery.getWaiter().getPeronSurname())
                        .append("\nStatus: ")
                        .append(delivery.getOrder().getOrderStatus())
                        .append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Deliveries", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showOnlyMyDeliveries() {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (Delivery delivery : Delivery.getDeliveryExtent()) {
            if (delivery.getWaiter() != loggedWaiter)
                continue;
            found = true;
            sb.append("Delivery ID: ")
                    .append(delivery.getDeliveryID())
                    .append("\nOrder ID: ")
                    .append(delivery.getOrder().getOrderID())
                    .append("\nStatus: ")
                    .append(delivery.getOrder().getOrderStatus())
                    .append("\n\n");
        }
        if (!found) {
            sb.append("No deliveries assigned.");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "My Deliveries", JOptionPane.INFORMATION_MESSAGE);
    }
}