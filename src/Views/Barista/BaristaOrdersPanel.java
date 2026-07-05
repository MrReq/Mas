package Views.Barista;
import Enums.OrderStatus;
import Models.Barista;
import Models.Order;
import Models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
public class BaristaOrdersPanel extends JPanel {
    private final Barista loggedBarista;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton prepareButton;
    private JButton detailsButton;
    private JButton acceptOrderButton;
    private JButton acceptOrdersButton;
    private final BaristaDashboardView parent;
    public BaristaOrdersPanel(Barista loggedBarista, BaristaDashboardView parent) {
        this.loggedBarista = loggedBarista;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        this.parent = parent;
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Order ID", "Client", "Products", "Status"});
        ordersTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        ordersTable.setRowSorter(sorter);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        acceptOrderButton = new JButton("Accept Order");
        acceptOrdersButton = new JButton("Accept Orders");
        refreshButton = new JButton("Refresh");
        detailsButton = new JButton("Details");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("NEW Orders Waiting For Preparation  (BaristaOrdersPanel)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(acceptOrderButton);
        bottomPanel.add(acceptOrdersButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(detailsButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        acceptOrderButton.addActionListener(e -> {acceptOrder();});
        acceptOrdersButton.addActionListener(e -> {acceptOrders();});
        refreshButton.addActionListener(e -> refreshTable());
        detailsButton.addActionListener(e -> showDetails());
    }
    // TABLE
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if (order.getOrderStatus() != OrderStatus.NEW)
                continue;
            System.out.println("Order: " + order.getOrderID());
            System.out.println("Products count: " + order.getProducts().size());
            StringBuilder products = new StringBuilder();
            for (Product product : order.getProducts()) {
                if (products.length() > 0)
                    products.append(", ");
                products.append(product.getProductName());
            }
            String clientName = "-";
            if (order.getClient() != null)
                clientName = order.getClient().getPersonName() + " " + order.getClient().getPeronSurname();
            tableModel.addRow(new Object[]{order.getOrderID(), clientName, products.toString(), order.getOrderStatus()});
        }
    }
    // DETAILS
    private void showDetails() {
        int row = ordersTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please select an order.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Order ID: " + tableModel.getValueAt(row,0)
                        + "\nClient: " + tableModel.getValueAt(row,1)
                        + "\nProducts: " + tableModel.getValueAt(row,2) + "\nStatus: " + (OrderStatus) tableModel.getValueAt(row,3)
        );
    }
    // PREPARE
    public void reload() {
        refreshTable();
    }
    private void acceptOrder() {
        int row = ordersTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Select order.");
            return;
        }
        int orderId = (Integer) tableModel.getValueAt(row,0);
        Order order = Order.findById(orderId);
        try{
            loggedBarista.acceptOrder(order);
            JOptionPane.showMessageDialog(this, "Order accepted.");
            parent.refreshAllPanels();
        }
        catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
    }
    private void acceptOrders() {
        int[] rows = ordersTable.getSelectedRows();
        if (rows.length == 0) {
            JOptionPane.showMessageDialog(this, "Select at least one order.");
            return;
        }
        for (int row : rows) {
            int orderId = (Integer) tableModel.getValueAt(row, 0);
            Order order = Order.findById(orderId);
            if (order != null)
                loggedBarista.acceptOrder(order);
        }
        JOptionPane.showMessageDialog(this, rows.length + " orders moved to Acceptes.");
        parent.refreshAllPanels();
    }
}