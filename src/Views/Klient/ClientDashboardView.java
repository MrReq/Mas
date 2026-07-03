package Views.Klient;

import Models.Client;


import Models.Client;
import Views.Loging.LoginSelectionView;

import javax.swing.*;
import java.awt.*;

public class ClientDashboardView extends JFrame {

    private final Client loggedClient;

    private JLabel welcomeLabel;

    private JButton logoutButton;

    private JTabbedPane tabbedPane;

    private ClientMenuPanel menuPanel;
    private ClientShoppingCartView clientShoppingCartView;
    private ClientProfileView clientProfileView;
    private ClientOrderHistoryPanel clientOrderHistoryView;

    public ClientDashboardView(Client client) {

        this.loggedClient = client;

        menuPanel = new ClientMenuPanel(loggedClient);
        clientShoppingCartView = new ClientShoppingCartView(loggedClient);
        clientProfileView = new ClientProfileView(loggedClient);
        clientOrderHistoryView = new ClientOrderHistoryPanel(loggedClient);

        initializeFrame();

        initializeComponents();

        initializeLayout();

        initializeListeners();

    }

    private void initializeFrame() {

        setTitle("Coffee House - Client Panel (ClientDashboardView)");

        setSize(1000,700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initializeComponents() {

        welcomeLabel = new JLabel(

                "Welcome " +

                        loggedClient.getPersonName() +

                        " " +

                        loggedClient.getPeronSurname()

        );

        welcomeLabel.setFont(new Font("Arial",Font.BOLD,20));

        logoutButton = new JButton("Logout");

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Menu", menuPanel);

        tabbedPane.addTab("Shopping Cart", clientShoppingCartView);

        tabbedPane.addTab("My Orders", clientOrderHistoryView);

        tabbedPane.addTab("Profile", clientProfileView);

    }

    private void initializeLayout() {

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(welcomeLabel,BorderLayout.WEST);

        topPanel.add(logoutButton,BorderLayout.EAST);

        setLayout(new BorderLayout());

        add(topPanel,BorderLayout.NORTH);

        add(tabbedPane,BorderLayout.CENTER);

    }

    private void initializeListeners() {




        logoutButton.addActionListener(e->{

            dispose();

            new LoginSelectionView().setVisible(true);

        });

    }

}
