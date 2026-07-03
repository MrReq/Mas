package Views.Employee;

import Models.Barista;
import Views.Loging.*;
import Views.Panels.Barista.*;

import javax.swing.*;
import java.awt.*;

public class BaristaDashboardView extends JFrame {
    private BaristaOrdersPanel ordersPanel;
    private BaristaPrepareCoffeePanel prepareCoffeePanel;
    private BaristaAcceptedOrdersPanel acceptedOrdersPanel;
    private BaristaFinishedOrdersPanel finishedOrdersPanel;

    private final Barista loggedBarista;

    private JTabbedPane tabbedPane;

    public BaristaDashboardView(Barista loggedBarista) {

        this.loggedBarista = loggedBarista;

        initializeFrame();

        initializeComponents();

        initializeLayout();

    }

    //=================================================
    // FRAME
    //=================================================

    private void initializeFrame() {

        setTitle("Coffee House - Barista Dashboard");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //=================================================
    // COMPONENTS
    //=================================================

    private void initializeComponents() {

        ordersPanel = new BaristaOrdersPanel(loggedBarista, this);
        acceptedOrdersPanel = new BaristaAcceptedOrdersPanel(loggedBarista,this);
        prepareCoffeePanel = new BaristaPrepareCoffeePanel(loggedBarista,this);
        finishedOrdersPanel = new BaristaFinishedOrdersPanel(loggedBarista, this);


        tabbedPane = new JTabbedPane();

        tabbedPane.addTab(
                "Orders",
                ordersPanel
        );

        tabbedPane.addTab(
                "Accepted",
                acceptedOrdersPanel
        );

        tabbedPane.addTab(
                "Preparing Coffee",
                prepareCoffeePanel
        );

        tabbedPane.addTab(
                "Finished Orders",
                finishedOrdersPanel
        );

        tabbedPane.addTab(
                "Menu",
                new BaristaMenuPanel()
        );

        tabbedPane.addTab(
                "Statistics",
                new BaristaStatisticsPanel(loggedBarista)
        );

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);

    }

    //=================================================
    // TOP PANEL
    //=================================================

    private JPanel createTopPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(

                "Logged as: "
                        + loggedBarista.getPersonName()
                        + " "
                        + loggedBarista.getPeronSurname()

        );

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

    public void refreshAllPanels() {

        ordersPanel.reload();

        acceptedOrdersPanel.reload();

        prepareCoffeePanel.reload();

        finishedOrdersPanel.reload();

    }

}