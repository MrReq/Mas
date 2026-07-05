package Views.Waiter;
import Models.Waiter;
import Views.Loging.LoginSelectionView;
import Views.Waiter.*;
import javax.swing.table.TableRowSorter;
import javax.swing.*;
import java.awt.*;
public class WaiterDashboardView extends JFrame {
    private final Waiter loggedWaiter;
    private JTabbedPane tabbedPane;
    private WaiterOrdersPanel waiterordersPanel;
    private WaiterServedOrdersPanel waiterservedOrdersPanel;
    private WaiterPaymentsPanel waiterPaymentsPanel;
    private WaiterStatisticsPanel waiterstatisticsPanel;
    public WaiterDashboardView( Waiter loggedWaiter) {
        waiterordersPanel = new WaiterOrdersPanel(loggedWaiter, this);
        waiterservedOrdersPanel = new WaiterServedOrdersPanel(loggedWaiter,this);
        waiterPaymentsPanel = new WaiterPaymentsPanel(loggedWaiter,this);
        waiterstatisticsPanel = new WaiterStatisticsPanel(loggedWaiter,this);
        this.loggedWaiter = loggedWaiter;
        initializeFrame();
        initializeComponents();
        initializeLayout();
    }
    // FRAME
    private void initializeFrame() {
        setTitle("Coffee House Management System - Waiter");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // COMPONENTS
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Orders", waiterordersPanel);
        tabbedPane.addTab("Served", waiterservedOrdersPanel);
        tabbedPane.addTab("Payments", waiterPaymentsPanel);
        tabbedPane.addTab("Statistics", waiterstatisticsPanel);
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    // TOP PANEL
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Logged as: " + loggedWaiter.getPersonName() + " " + loggedWaiter.getPeronSurname());
        title.setFont(new Font("Arial", Font.BOLD, 22));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginSelectionView().setVisible(true);});
        panel.add(title, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        return panel;
    }
    public void refreshAllPanels() {
        waiterordersPanel.reload();
        waiterservedOrdersPanel.reload();
        waiterPaymentsPanel.reload();
        waiterstatisticsPanel.reload();
    }
}
