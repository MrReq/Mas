package Views.Panels.Barista;

import Enums.OrderStatus;
import Models.Barista;
import Models.Order;
import Models.Product;
import Views.Employee.BaristaDashboardView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BaristaPrepareCoffeePanel extends JPanel {

    private final Barista loggedBarista;

    private JTable coffeeTable;

    private DefaultTableModel tableModel;

    private JButton refreshButton;

    private JButton startPreparationButton;

    private JButton finishPreparationButton;
    private BaristaDashboardView parent;

    public BaristaPrepareCoffeePanel(Barista loggedBarista, BaristaDashboardView parent) {

        this.loggedBarista = loggedBarista;
        this.parent = parent;

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

                "Order ID",
                "Coffee",
                "Quantity",
                "Temperature",
                "Status"

        });

        coffeeTable = new JTable(tableModel);

        coffeeTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        refreshButton = new JButton("Refresh");

        startPreparationButton =
                new JButton("Start preparation");

        finishPreparationButton =
                new JButton("Coffee ready");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "Coffee Preparation (BaristaPrepareCoffeePanel)",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        add(new JScrollPane(coffeeTable),
                BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(refreshButton);

        bottomPanel.add(startPreparationButton);

        bottomPanel.add(finishPreparationButton);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        refreshButton.addActionListener(
                e -> refreshTable()
        );

        startPreparationButton.addActionListener(
                e -> startPreparation()
        );

        finishPreparationButton.addActionListener(
                e -> finishPreparation()
        );

    }

    //=================================================
    // TABLE
    //=================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Order order : Order.getOrderExtent()) {

            if (order.getOrderStatus() == OrderStatus.PREPARING) {

                String products = "";

                for (Product product : order.getProducts()) {

                    if (!products.isEmpty()) {
                        products += ", ";
                    }

                    products += product.getProductName();

                }

                String temperature = "-";

                if (!order.getProducts().isEmpty()) {

                    Product firstProduct = order.getProducts().get(0);

                    if (firstProduct.getTemperatureOfTheService() != null) {

                        temperature = firstProduct
                                .getTemperatureOfTheService()
                                .toString();

                    }

                }

                tableModel.addRow(new Object[]{

                        order.getOrderID(),
                        products,
                        order.getProducts().size(),
                        temperature,
                        order.getOrderStatus()

                });

            }

        }

    }

    //=================================================
    // START PREPARATION
    //=================================================

    private void startPreparation() {

        int row = coffeeTable.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(

                    this,

                    "Please select coffee."

            );

            return;

        }

        tableModel.setValueAt(

                "Preparing",

                row,

                4

        );

        JOptionPane.showMessageDialog(

                this,

                "Coffee preparation started."

        );

    }

    //=================================================
    // FINISH PREPARATION
    //=================================================

    private void finishPreparation() {

        int row = coffeeTable.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(

                    this,

                    "Please select coffee."

            );

            return;

        }
        int orderID = (Integer) tableModel.getValueAt(row, 0);
        Order selectedOrder = Order.findById(orderID);
        loggedBarista.markOrderAsReady(selectedOrder);

        JOptionPane.showMessageDialog(

                this,

                "Coffee is ready to serve."

        );
        parent.refreshAllPanels();
    }

    public void reload() {
        refreshTable();
    }

}