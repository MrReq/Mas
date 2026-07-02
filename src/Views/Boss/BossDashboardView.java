package Views.Boss;


import Models.Boss;
import Views.Loging.LoginSelectionView;
import Views.Panels.*;
import Views.Panels.Client.ClientsPanel;

import javax.swing.*;
import java.awt.*;

public class BossDashboardView extends JFrame {

    private final JTabbedPane tabbedPane;

    private final Boss loggedBoss;

    public BossDashboardView(Boss boss) {

        setTitle("Coffee House Management System - Owner Panel (BossDashboardView)");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        this.loggedBoss = boss;
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Products", new BossProductsPanel(loggedBoss));
        tabbedPane.addTab("Employees", new ManageEmployeesView(loggedBoss));
//        tabbedPane.addTab("Clients", new ClientsPanel(loggedBoss));
//        tabbedPane.addTab("Orders", new OrdersPanel(loggedBoss));
//        tabbedPane.addTab("Statistics", new StatisticsPanel(loggedBoss));
//        tabbedPane.addTab("Extents", new ExtentsPanel(loggedBoss));

        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

    }

    private JPanel createTopPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Coffee House - Owner Dashboard (BossDashBoardView)");

        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(e -> {

            dispose();

            new LoginSelectionView().setVisible(true);

        });

        panel.add(title, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);

        return panel;
    }

}
