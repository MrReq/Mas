package Views.Waiter;
import Models.Waiter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class WaiterPaymentsPanel extends JPanel {
    private final Waiter waiter;
    private JTable paymentsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton acceptButton;
    public WaiterPaymentsPanel(Waiter waiter) {
        this.waiter = waiter;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }

    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Payment ID", "Order", "Client", "Amount", "Status"});
        paymentsTable = new JTable(tableModel);
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
        tableModel.addRow(new Object[]{1,10,"John",42.50,"WAITING"});
        tableModel.addRow(new Object[]{2,11,"Anna",25.00,"PAID"});
        tableModel.addRow(new Object[]{3,12,"Mark",18.50,"WAITING"});
    }

    private void acceptPayment() {
        int row = paymentsTable.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select payment.");
            return;
        }
        tableModel.setValueAt("PAID",row,4);
        JOptionPane.showMessageDialog(this, "Payment accepted."
        );
    }
    public void reload(){
        refreshTable();
    }
}