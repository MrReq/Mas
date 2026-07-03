package Views.Panels;

import Models.Product;
import Views.Panels.ShoppingCartPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductsPanel extends JPanel {

    private JTable productsTable;

    private DefaultTableModel tableModel;

    private JButton refreshButton;

    private JButton addToCartButton;

    private final ShoppingCartPanel shoppingCartPanel;

    public ProductsPanel(ShoppingCartPanel shoppingCartPanel) {

        this.shoppingCartPanel = shoppingCartPanel;

        initializeComponents();

        initializeLayout();

        initializeListeners();

        refreshTable();

    }

    //=========================================================
    // COMPONENTS
    //=========================================================

    private void initializeComponents() {

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{

                "Name",
                "Price",
                "Available",
                "Temperature"

        });

        productsTable = new JTable(tableModel);

        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        refreshButton = new JButton("Refresh");

        addToCartButton = new JButton("Add to cart");

    }

    //=========================================================
    // LAYOUT
    //=========================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        add(new JScrollPane(productsTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(refreshButton);

        bottomPanel.add(addToCartButton);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    //=========================================================
    // LISTENERS
    //=========================================================

    private void initializeListeners() {

        refreshButton.addActionListener(e -> refreshTable());

        addToCartButton.addActionListener(e -> addSelectedProductToCart());

    }

    //=========================================================
    // TABLE
    //=========================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Product product : Product.getProductExtent()) {

            tableModel.addRow(new Object[]{

                    product.getProductName(),

                    product.getProductCost(),

                    product.isProductAvailability(),

                    product.getTemperatureOfTheService()

            });

        }

    }

    //=========================================================
    // CART
    //=========================================================

    private void addSelectedProductToCart() {

        int selectedRow = productsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(

                    this,

                    "Please select a product first.",

                    "No selection",

                    JOptionPane.WARNING_MESSAGE

            );

            return;

        }

        Product selectedProduct = Product.getProductExtent().get(selectedRow);

        shoppingCartPanel.addProduct(selectedProduct);

        JOptionPane.showMessageDialog(

                this,

                selectedProduct.getProductName()
                        + " has been added to the shopping cart."

        );

    }

}