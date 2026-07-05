package Views.Barista;
import Enums.OrderStatus;
import Models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class BaristaPrepareCoffeePanel extends JPanel {
    private final Barista loggedBarista;
    private JTable coffeeTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
//    private JButton startPreparationButton;
    private JButton finishPreparationButton;
    private JButton countPowerOfCoffeeButton;
    private BaristaDashboardView parent;
    private Preparation preparation;
    public BaristaPrepareCoffeePanel(Barista loggedBarista, BaristaDashboardView parent) {
        this.loggedBarista = loggedBarista;
        this.parent = parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    // COMPONENTS
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Order ID", "Person Name","Products", "Quantity", "Price", "StartTime","PreparationTime","My Barista's Name", "Status"});
        coffeeTable = new JTable(tableModel);
        coffeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coffeeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        refreshButton = new JButton("Refresh");
//        startPreparationButton = new JButton("Start preparation");
        finishPreparationButton = new JButton("Coffee ready");
        countPowerOfCoffeeButton = new JButton("Count Power Of Coffee");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Coffee Preparation (BaristaPrepareCoffeePanel)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(coffeeTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
//        bottomPanel.add(startPreparationButton);
        bottomPanel.add(finishPreparationButton);
        bottomPanel.add(countPowerOfCoffeeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
//        startPreparationButton.addActionListener(e -> startPreparation());
        finishPreparationButton.addActionListener(e -> finishPreparation());
        countPowerOfCoffeeButton.addActionListener(e -> finishPreparation());
    }
    // TABLE
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if(order.getOrderStatus()!=OrderStatus.PREPARING)
                continue;

            if(order.getPreparation()==null)
                continue;

            if(order.getPreparation().getBarista()!=loggedBarista)
                continue;
            String productsText;
            if (order.getProducts().isEmpty())
                productsText = "No products";
             else {
                StringBuilder builder = new StringBuilder();
                for (Product product : order.getProducts()) {
                    if (builder.length() > 0)
                        builder.append(", ");
                    builder.append(product.getProductName());
                }
                productsText = builder.toString();
            }
            tableModel.addRow(new Object[]{order.getOrderID(), order.getClient(), productsText, order.getProducts().size(),
                     order.getTotalPrice(),
                    preparation != null
                    ? preparation.getStartTime()
                    : "-",

                    preparation != null
                            ? preparation.getPreparationTime()
                            : "-",

                    preparation != null && preparation.getBarista() != null
                            ? preparation.getBarista().getPersonName()
                            : "-",
                    order.getOrderStatus()
            });
        }
    }
    // START PREPARATION
//    private void startPreparation() {
//        int row = coffeeTable.getSelectedRow();
//        if (row == -1) {JOptionPane.showMessageDialog(this, "Please select order.");
//            return;
//        }
//        int orderId = (Integer) tableModel.getValueAt(row, 0);
//        Order order = Order.findById(orderId);
//        loggedBarista.startPreparing(order);
//        parent.refreshAllPanels();
//    }
    // FINISH PREPARATION
    private void finishPreparation() {
        int row = coffeeTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please select coffee.");
            return;
        }
        int orderID = (Integer) tableModel.getValueAt(row, 0);
        Order selectedOrder = Order.findById(orderID);
        loggedBarista.markOrderAsReady(selectedOrder);
        JOptionPane.showMessageDialog(this, "Coffee is ready to serve.");
        parent.refreshAllPanels();
    }

//    public void setCountPowerOfCoffeeButton(){
//        int row = coffeeTable.getSelectedRow();
//        if(row == -1){
//            JOptionPane.showMessageDialog(this, "Please select coffee.");
//            return;
//        }
//        int orderID = (Integer) tableModel.getValueAt(row, 0);
//        Order selectedOrder = Order.findById(orderID);
//        Coffee coffee = (Coffee) selectedOrder.getProducts().getFirst();
//    }
    public void reload() {refreshTable();}
}