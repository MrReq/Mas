package Views.Panels;

import Models.Boss;
import Models.Product;
import Views.Boss.AddNewProductView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BossProductsPanel extends JPanel {

    //=================================================
    // FIELDS
    //=================================================

    private final Boss loggedBoss;

    private JTable productsTable;

    private DefaultTableModel tableModel;

    private JButton refreshButton;

    private JButton addButton;

    private JButton editButton;

    private JButton deleteButton;

    //=================================================
    // CONSTRUCTOR
    //=================================================

    public BossProductsPanel(Boss boss) {

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

        tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new Object[]{

                "ID",
                "Name",
                "Price",
                "Available"

        });

        productsTable = new JTable(tableModel);

        productsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        refreshButton = new JButton("Refresh");

        addButton = new JButton("Add Product");

        editButton = new JButton("Edit Product");

        deleteButton = new JButton("Delete Product");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel(

                "Product Management",

                SwingConstants.CENTER

        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        add(new JScrollPane(productsTable),
                BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(addButton);

        bottomPanel.add(editButton);

        bottomPanel.add(deleteButton);

        bottomPanel.add(refreshButton);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        refreshButton.addActionListener(e -> refreshTable());

        addButton.addActionListener(e -> createProduct());

        editButton.addActionListener(e -> editProduct());

        deleteButton.addActionListener(e -> deleteProduct());

    }

    //=================================================
    // TABLE
    //=================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for(Product product : Product.getExtent()){

            tableModel.addRow(new Object[]{

                    product.getProductID(),

                    product.getProductName(),

                    product.getProductCost(),

                    product.isProductAvailability()

            });

        }

    }

    //=================================================
    // ADD PRODUCT
    //=================================================

    private void createProduct() {
        new AddNewProductView().setVisible(true);
        refreshTable();
    }

    //=================================================
    // EDIT PRODUCT
    //=================================================

    private void editProduct() {

        int row = productsTable.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(

                    this,

                    "Select product first."

            );

            return;

        }

        Product product =
                Product.getExtent().get(row);

        JOptionPane.showMessageDialog(

                this,

                "Editing: " +

                        product.getProductName()

        );

        refreshTable();

    }

    //=================================================
    // DELETE PRODUCT
    //=================================================

    private void deleteProduct() {

        int row = productsTable.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(

                    this,

                    "Select product first."

            );

            return;

        }

        int option = JOptionPane.showConfirmDialog(

                this,

                "Delete selected product?",

                "Confirmation",

                JOptionPane.YES_NO_OPTION

        );

        if(option != JOptionPane.YES_OPTION){

            return;

        }

        Product product =
                Product.getExtent().get(row);

        // TODO
        // remove from extent after migration to ObjectPlus

        JOptionPane.showMessageDialog(

                this,

                product.getProductName()

                        + " removed."

        );

        refreshTable();

    }

}