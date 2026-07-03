package Views.Klient;

import Models.Client;

import javax.swing.*;
import java.awt.*;

public class ClientProfileView extends JPanel {

    private final Client loggedClient;

    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthDateLabel;
    private JLabel sexLabel;
    private JLabel clubCardLabel;
    private JLabel ordersLabel;

    private JButton refreshButton;
    private JButton editButton;

    public ClientProfileView(Client loggedClient) {

        this.loggedClient = loggedClient;

        initializeComponents();
        initializeLayout();
        initializeListeners();

        refreshData();
    }

    //=================================================
    // COMPONENTS
    //=================================================

    private void initializeComponents() {

        nameLabel = new JLabel();
        surnameLabel = new JLabel();
        birthDateLabel = new JLabel();
        sexLabel = new JLabel();
        clubCardLabel = new JLabel();
        ordersLabel = new JLabel();

        refreshButton = new JButton("Refresh");
        editButton = new JButton("Edit profile");
    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "CLIENT PROFILE",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(6, 2, 10, 10));

        center.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        center.add(new JLabel("Name:"));
        center.add(nameLabel);

        center.add(new JLabel("Surname:"));
        center.add(surnameLabel);

        center.add(new JLabel("Birth date:"));
        center.add(birthDateLabel);

        center.add(new JLabel("Sex:"));
        center.add(sexLabel);

        center.add(new JLabel("Club card:"));
        center.add(clubCardLabel);

        center.add(new JLabel("Orders:"));
        center.add(ordersLabel);

        add(center, BorderLayout.CENTER);

        JPanel south = new JPanel();

        south.add(refreshButton);
        south.add(editButton);

        add(south, BorderLayout.SOUTH);
    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        refreshButton.addActionListener(e -> refreshData());

        editButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Profile editing will be implemented soon."
                )
        );
    }

    //=================================================
    // REFRESH
    //=================================================

    private void refreshData() {

        nameLabel.setText(loggedClient.getPersonName());

        surnameLabel.setText(loggedClient.getPeronSurname());

        birthDateLabel.setText(
                loggedClient.getPersonDateOfBirth().toString()
        );

        sexLabel.setText(
                loggedClient.getPersonSex().toString()
        );

        clubCardLabel.setText(
                loggedClient.hasClubCard()
                        ? "Yes"
                        : "No"
        );

        ordersLabel.setText(
                String.valueOf(
                        loggedClient.getOrders().size()
                )
        );
    }
}