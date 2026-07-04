package Views.Barista;
import Enums.OrderStatus;
import Models.Barista;
import Models.Order;
import Models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class BaristaFinishedOrdersPanel extends JPanel {
    private final Barista loggedBarista;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton completeButton;
    private BaristaDashboardView parent;
    public BaristaFinishedOrdersPanel(Barista loggedBarista, BaristaDashboardView parent) {
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
        refreshButton = new JButton("Refresh");
//        completeButton = new JButton("Complete Order");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Ready Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
//        bottomPanel.add(completeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
//        completeButton.addActionListener(e -> completeOrder());
    }
    // TABLE
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if(order.getOrderStatus()!=OrderStatus.READY)
                continue;

            if(order.getPreparation()==null)
                continue;

            if(order.getPreparation().getBarista()!=loggedBarista)
                continue; {
                String products = "";
                for (Product product : order.getProducts()) {
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
    // COMPLETE
//    private void completeOrder() {
//        int row = ordersTable.getSelectedRow();
//        if (row == -1) {JOptionPane.showMessageDialog(this, "Please select an order.");
//            return;
//        }
//        int orderId = (Integer) tableModel.getValueAt(row, 0);
//        Order order = Order.findById(orderId);
//        loggedBarista.completeOrder(order);
//        parent.refreshAllPanels();
//    }
    public void reload() {
        refreshTable();
    }
}