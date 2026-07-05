package Views.Barista;
import Models.Barista;
import Models.Client;
import Views.Loging.*;
import javax.swing.*;
import java.awt.*;
import Enums.Citizenship;
public class BaristaDashboardView extends JFrame {
    private BaristaOrdersPanel ordersPanel;
    private BaristaMenuPanel baristaMenuPanel;
    private BaristaAcceptedOrdersPanel acceptedOrdersPanel;
    private BaristaPrepareCoffeePanel prepareCoffeePanel;
    private BaristaFinishedOrdersPanel finishedOrdersPanel;
    private BaristaStatisticsPanel baristaStatisticsPanel;
    private final Barista loggedBarista;
    private JTabbedPane tabbedPane;
    public BaristaDashboardView(Barista loggedBarista) {
        this.loggedBarista = loggedBarista;
        initializeFrame();
        initializeComponents();
        initializeLayout();
    }
    // FRAME
    private void initializeFrame() {
        setTitle("Coffee House - Barista Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // COMPONENTS
    private void initializeComponents() {
        ordersPanel = new BaristaOrdersPanel(loggedBarista, this);
        baristaMenuPanel = new BaristaMenuPanel(loggedBarista, this);
        acceptedOrdersPanel = new BaristaAcceptedOrdersPanel(loggedBarista,this);
        prepareCoffeePanel = new BaristaPrepareCoffeePanel(loggedBarista,this);
        finishedOrdersPanel = new BaristaFinishedOrdersPanel(loggedBarista, this);
        baristaStatisticsPanel = new BaristaStatisticsPanel(loggedBarista, this);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Orders", ordersPanel);
        tabbedPane.addTab("Accepted", acceptedOrdersPanel);
        tabbedPane.addTab("Preparing Coffee", prepareCoffeePanel);
        tabbedPane.addTab("Finished Orders", finishedOrdersPanel);
        tabbedPane.addTab("Menu", baristaMenuPanel);
        tabbedPane.addTab("Statistics", baristaStatisticsPanel);
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
        JLabel title = new JLabel("Logged as: " + loggedBarista.getPersonName() + " " + loggedBarista.getPeronSurname());
        title.setFont(new Font("Arial", Font.BOLD, 22));
        JButton logoutButton = new JButton("Logout");
        JButton becomeClientButton = new JButton("Become Client");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginSelectionView().setVisible(true);
        });
        becomeClientButton.addActionListener(e->{becomeClient();});
        panel.add(title, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        panel.add(becomeClientButton, BorderLayout.CENTER);
        return panel;
    }
    public void refreshAllPanels() {
        ordersPanel.reload();
        acceptedOrdersPanel.reload();
        prepareCoffeePanel.reload();
        finishedOrdersPanel.reload();
        baristaStatisticsPanel.reload();
    }
    private void becomeClient() {
        if (Client.isClient(loggedBarista)) {
            JOptionPane.showMessageDialog(this, "You are already a client.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to become a client?",
                "Overlapping", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION)
            return;
        loggedBarista.becomeClient(true, Citizenship.Native);
        JOptionPane.showMessageDialog(this, "You are now both a Barista and a Client."
        );
    }
}