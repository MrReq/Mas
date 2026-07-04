package Views.Waiter;
import Models.Waiter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class WaiterTablesPanel extends JPanel {
    private final Waiter waiter;
    private JTable tablesTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton reserveButton;
    private JButton freeButton;
    public WaiterTablesPanel(Waiter waiter) {
        this.waiter = waiter;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshTable();
    }
    private void initializeComponents() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Table", "Seats", "Status", "Client"
        });
        tablesTable = new JTable(tableModel);
        tablesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshButton = new JButton("Refresh");
        reserveButton = new JButton("Reserve");
        freeButton = new JButton("Free Table");
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(new JScrollPane(tablesTable), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        bottom.add(reserveButton);
        bottom.add(freeButton);
        add(bottom, BorderLayout.SOUTH);
    }
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshTable());
        reserveButton.addActionListener(e -> reserveTable());
        freeButton.addActionListener(e -> freeTable());
    }
    private void refreshTable() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{1,4,"FREE","-"});
        tableModel.addRow(new Object[]{2,2,"BUSY","John"});
        tableModel.addRow(new Object[]{3,6,"RESERVED","Anna"});
        tableModel.addRow(new Object[]{4,4,"FREE","-"});
    }
    private void reserveTable() {
        int row = tablesTable.getSelectedRow();
        if(row==-1){JOptionPane.showMessageDialog(this,"Select table.");
            return;
        }
        tableModel.setValueAt("RESERVED",row,2);
    }
    private void freeTable() {
        int row = tablesTable.getSelectedRow();
        if(row==-1){
            JOptionPane.showMessageDialog(this,"Select table.");
            return;
        }
        tableModel.setValueAt("FREE",row,2);
        tableModel.setValueAt("-",row,3);
    }
    public void reload(){
        refreshTable();
    }
}