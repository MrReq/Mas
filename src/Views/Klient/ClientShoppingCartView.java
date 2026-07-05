package Views.Klient;
import Models.Client;
import Models.Order;
import Models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
public class ClientShoppingCartView extends JPanel {
    private final Client loggedClient;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JButton refreshButton;
    private JButton removeButton;
    private JButton placeOrderButton;
    private ClientDashboardView parent;
    public ClientShoppingCartView(Client loggedClient, ClientDashboardView parent) {
        this.loggedClient = loggedClient;
        this.parent = parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Product", "Price"});
        cartTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        cartTable.setRowSorter(sorter);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        totalLabel = new JLabel("Total: 0.00 zł");
        refreshButton = new JButton("Refresh");
        removeButton = new JButton("Remove");
        placeOrderButton = new JButton("Place order");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("SHOPPING CART", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        buttons.add(refreshButton);
        buttons.add(removeButton);
        buttons.add(placeOrderButton);
        bottom.add(totalLabel, BorderLayout.WEST);
        bottom.add(buttons, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        removeButton.addActionListener(e -> removeProduct());
        placeOrderButton.addActionListener(e -> placeOrder());
    }
    // REFRESH
    private void refreshTable() {
        tableModel.setRowCount(0);
        Order cart = loggedClient.getShoppingCart();
        if (cart == null)
            return;
        double total = 0;
        for (Product product : cart.getProducts()) {
            tableModel.addRow(new Object[]{product.getProductID(), product.getProductName(), product.getProductCost()});
            total += product.getProductCost();
        }
        totalLabel.setText(String.format("Total: %.2f zł", total));
    }
    // REMOVE PRODUCT
    private void removeProduct() {
        int row = cartTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select product.");
            return;
        }
        Order cart = loggedClient.getShoppingCart();
        Product product = cart.getProducts().get(row);
        cart.removeProduct(product);
        refreshTable();
    }
    // PLACE ORDER
    private void placeOrder() {
        Order cart = loggedClient.getShoppingCart();
        if (cart == null || cart.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Shopping cart is empty.");
            return;
        }
        cart.setShoppingCart(false);
        JOptionPane.showMessageDialog(this, "Order placed successfully.");
        loggedClient.createNewShoppingCart();
        refreshTable();
        parent.refreshAllPanels();
    }
    public void reload() {
        refreshTable();
    }
}