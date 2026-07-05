package Views.Waiter;
import Enums.OrderStatus;
import Models.Waiter;
import Models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableRowSorter;
public class WaiterPaymentsPanel extends JPanel {
    private final Waiter waiter;
    private JTable paymentsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton acceptButton;
    private WaiterDashboardView parent;
    public WaiterPaymentsPanel(Waiter waiter, WaiterDashboardView parent) {
        this.waiter = waiter;
        this.parent=parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Payment ID", "Order", "Client", "Amount", "Status"});
        paymentsTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        paymentsTable.setRowSorter(sorter);
        paymentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshButton = new JButton("Refresh");
        acceptButton = new JButton("Accept Payment");
    }
    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(new JScrollPane(paymentsTable),BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(acceptButton);
        add(bottom,BorderLayout.SOUTH);
    }
    private void initializeListeners() {
        refreshButton.addActionListener(e->refreshTable());
        acceptButton.addActionListener(e->acceptPayment());
    }
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Order order : Order.getOrderExtent()) {
            if (order.getOrderStatus() != OrderStatus.PAID)
                continue;
            StringBuilder products = new StringBuilder();
            for (Product product : order.getProducts()) {
                if (products.length() > 0)
                    products.append(", ");
                products.append(product.getProductName());
            }
            tableModel.addRow(new Object[]{
                    order.getOrderID(),
                    order.getClient().getPersonName() + " " + order.getClient().getPeronSurname(),
                    products.toString(),
                    order.getTotalPrice(),
                    order.getOrderStatus()
            });
        }
    }
    private void acceptPayment() {
        int row = paymentsTable.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select payment.");
            return;
        }
        tableModel.setValueAt("PAID",row,4);
        JOptionPane.showMessageDialog(this, "Payment accepted.");
        parent.refreshAllPanels();
    }
    public void reload(){
        refreshTable();
    }
}