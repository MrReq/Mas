package Views.Panels.Barista;

import Models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BaristaMenuPanel extends JPanel {

    private JTable menuTable;

    private DefaultTableModel tableModel;

    private JButton refreshButton;

    private JButton detailsButton;

    public BaristaMenuPanel() {

        initializeComponents();

        initializeLayout();

        initializeListeners();

        refreshTable();

    }

    //=================================================
    // COMPONENTS
    //=================================================

    private void initializeComponents() {

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new String[]{

                "Name",
                "Price",
                "Available",
                "Temperature"

        });

        menuTable = new JTable(tableModel);

        menuTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        refreshButton = new JButton("Refresh");

        detailsButton = new JButton("Show Details");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "Coffee House Menu (BaristaMenuPanel)",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        add(new JScrollPane(menuTable),
                BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(refreshButton);

        bottomPanel.add(detailsButton);

        add(bottomPanel,
                BorderLayout.SOUTH);

    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        refreshButton.addActionListener(
                e -> refreshTable()
        );

        detailsButton.addActionListener(
                e -> showDetails()
        );

    }

    //=================================================
    // TABLE
    //=================================================

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

    //=================================================
    // DETAILS
    //=================================================

    private void showDetails() {

        int row = menuTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(

                    this,

                    "Please select a product.",

                    "No Selection",

                    JOptionPane.WARNING_MESSAGE

            );

            return;

        }

        Product product = Product.getProductExtent().get(row);

        String ingredients;

        if (product.getProductIngredients() == null
                || product.getProductIngredients().isEmpty()) {

            ingredients = "No ingredients available.";

        } else {

            ingredients = String.join(
                    ", ",
                    product.getProductIngredients()
            );

        }

        JOptionPane.showMessageDialog(

                this,

                "Name: " + product.getProductName()
                        + "\n\nPrice: " + product.getProductCost() + " PLN"
                        + "\n\nAvailable: " + product.isProductAvailability()
                        + "\n\nTemperature: " + product.getTemperatureOfTheService()
                        + "\n\nDescription:\n" + product.getProductDescription()
                        + "\n\nIngredients:\n" + ingredients,

                "Product Details",

                JOptionPane.INFORMATION_MESSAGE

        );

    }

}