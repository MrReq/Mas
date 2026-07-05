package Views.Klient;
import Models.Client;
import Models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ClientMenuPanel extends JPanel {
    private final Client loggedClient;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton addToCartButton;
    private ClientDashboardView parent;
    public ClientMenuPanel(Client loggedClient, ClientDashboardView parent) {
        this.loggedClient = loggedClient;
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
                        "ID",
                        "Name",
                        "Price",
                        "Temperature",
                        "Available"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTable = new JTable(tableModel);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshButton = new JButton("Refresh");
        addToCartButton = new JButton("Add to cart");
    }
    // LAYOUT

    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("MENU", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(productsTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(addToCartButton);
        add(bottom, BorderLayout.SOUTH);
    }
    // LISTENERS

    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        addToCartButton.addActionListener(e -> addToCart());
    }
    // TABLE

    public void refreshTable() {
        System.out.println(Product.getProductExtent().size());
        tableModel.setRowCount(0);
        for (Product product : Product.getProductExtent()) {
            tableModel.addRow(new Object[]{product.getProductID(), product.getProductName(), product.getProductCost(),
                    product.getTemperatureOfTheService(), product.isProductAvailability()
            });
        }
    }
    // ADD TO CART

    private void addToCart() {
        int row = productsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select product.");
            return;
        }
        int productID = (Integer) tableModel.getValueAt(row, 0);
        Product selectedProduct = Product.findById(productID);
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Product not found.");
            return;
        }
        if (!selectedProduct.isProductAvailability()) {
            JOptionPane.showMessageDialog(this, "Product is unavailable.");
            return;
        }
        loggedClient.getShoppingCart().addProduct(selectedProduct);
        JOptionPane.showMessageDialog(this, "Product added to shopping cart."
        );
        parent.refreshAllPanels();
    }
    public void reload() {
        refreshTable();
    }
}