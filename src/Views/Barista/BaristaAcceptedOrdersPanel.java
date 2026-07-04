package Views.Barista;
import Enums.OrderStatus;
import Models.Barista;
import Models.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class BaristaAcceptedOrdersPanel extends JPanel {
    private final Barista loggedBarista;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton prepareOrderButton;
    private JButton prepareOrdersButton;
    private BaristaDashboardView parent;
    public BaristaAcceptedOrdersPanel(Barista loggedBarista, BaristaDashboardView parent) {
        this.loggedBarista = loggedBarista;
        this.parent = parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel(
                new Object[]{
                        "Order ID",
                        "Client",
                        "Products",
                        "Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(tableModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        refreshButton = new JButton("Refresh");
        prepareOrderButton = new JButton("Prepare Order");
        prepareOrdersButton = new JButton("Prepare Orders");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Accepted Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        bottomPanel.add(prepareOrderButton);
        bottomPanel.add(prepareOrdersButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        prepareOrderButton.addActionListener(e -> prepareOrder());
        prepareOrdersButton.addActionListener(e -> prepareOrders());
    }
    // TABLE
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
                String products = "";
                for (var product : order.getProducts()) {
                    if (!products.isEmpty()) {
                        products += ", ";
                    }
                    products += product.getProductName();
                }
                tableModel.addRow(new Object[]{order.getOrderID(), order.getClient().getPersonName(), products,
                        order.getOrderStatus()
                });
            }
        }
    }
    // PREPARE
    private void prepareOrder() {
        int row = ordersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order.");
            return;
        }
        int orderId = (Integer) tableModel.getValueAt(row, 0);
        Order order = Order.findById(orderId);
        loggedBarista.startPreparing(order);
        parent.refreshAllPanels();
    }
    
    private void prepareOrders() {
        int[] rows = ordersTable.getSelectedRows();
        if (rows.length == 0) {
            JOptionPane.showMessageDialog(this, "Select at least one order.");
            return;
        }
        for (int row : rows) {
            int orderId = (Integer) tableModel.getValueAt(row, 0);
            Order order = Order.findById(orderId);
            if (order != null)
                loggedBarista.startPreparing(order);
        }
        JOptionPane.showMessageDialog(this, rows.length + " orders moved to Preparing.");
        parent.refreshAllPanels();
    }
    public void reload() {
        refreshTable();
    }
}