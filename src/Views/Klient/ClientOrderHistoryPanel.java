package Views.Klient;
import Models.Client;
import Models.Order;
import Models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
public class ClientOrderHistoryPanel extends JPanel {
    private final Client loggedClient;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton detailsButton;

    public ClientOrderHistoryPanel(Client loggedClient) {
        this.loggedClient = loggedClient;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Order ID", "Date", "Products", "Status", "Value"});
        ordersTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        ordersTable.setRowSorter(sorter);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshButton = new JButton("Refresh");
        detailsButton = new JButton("Details");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("ORDER HISTORY  (ClientOrderHistoryPanel)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(detailsButton);
        add(bottom, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        detailsButton.addActionListener(e -> showDetails());
    }
    // REFRESH
    private void refreshTable() {
        tableModel.setRowCount(0);
        Order shoppingCart = loggedClient.getShoppingCart();
        for (Order order : loggedClient.getOrders()) {
            if (shoppingCart != null && order.getOrderID() == shoppingCart.getOrderID())
                continue;
            StringBuilder products = new StringBuilder();
            double value = 0;
            for (Product product : order.getProducts()) {
                if (!products.isEmpty())
                    products.append(", ");
                products.append(product.getProductName());
                value += product.getProductCost();
            }
            tableModel.addRow(new Object[]{order.getOrderID(), order.getCreatedAt(), products.toString(),
                order.getOrderStatus(), value
            });
        }
    }
    // DETAILS

    private void showDetails() {
        int row = ordersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select order.");
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Order ID: " + tableModel.getValueAt(row,0) +
                        "\nDate: " + tableModel.getValueAt(row,1) +
                        "\nProducts: " + tableModel.getValueAt(row,2) +
                        "\nStatus: " + tableModel.getValueAt(row,3) +
                        "\nValue: " + tableModel.getValueAt(row,4) + " zł");
    }
    public void reload() {
        refreshTable();
    }
}