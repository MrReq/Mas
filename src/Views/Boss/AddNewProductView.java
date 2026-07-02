package Views.Boss;

import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Models.*;

import javax.swing.*;
import java.awt.*;

public class AddNewProductView extends JFrame {

    //=================================================
    // COMPONENTS
    //=================================================

    private JTextField nameField;

    private JTextField priceField;

    private JTextArea descriptionArea;

    private JCheckBox availabilityCheckBox;

    private JComboBox<TemperatureOfTheService> temperatureComboBox;

    private JComboBox<String> typeComboBox;
    private JComboBox<CoffeeCountry> coffeeCountryComboBox;

    private JButton createButton;

    private JButton cancelButton;

    //=================================================
    // CONSTRUCTOR
    //=================================================

    public AddNewProductView() {

        initializeFrame();

        initializeComponents();

        initializeLayout();

        initializeListeners();

    }

    //=================================================
    // FRAME
    //=================================================

    private void initializeFrame() {

        setTitle("Create Product");

        setSize(600, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);

    }

    //=================================================
    // COMPONENTS
    //=================================================

    private void initializeComponents() {

        nameField = new JTextField();

        priceField = new JTextField();

        descriptionArea = new JTextArea(5,20);

        availabilityCheckBox = new JCheckBox("Available");

        temperatureComboBox =
                new JComboBox<>(TemperatureOfTheService.values());

        typeComboBox = new JComboBox<>();

        typeComboBox.addItem("Americano");

        typeComboBox.addItem("Cafe Latte");

        typeComboBox.addItem("Tea");

        coffeeCountryComboBox =
                new JComboBox<>(CoffeeCountry.values());

        createButton = new JButton("Create");

        cancelButton = new JButton("Cancel");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        JPanel panel = new JPanel(new GridBagLayout());
        setExtendedState(MAXIMIZED_BOTH);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product name:"), gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        panel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Temperature:"), gbc);

        gbc.gridx = 1;
        panel.add(temperatureComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Product type:"), gbc);

        gbc.gridx = 1;
        panel.add(typeComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        panel.add(availabilityCheckBox, gbc);

        JPanel buttons = new JPanel();

        buttons.add(createButton);

        buttons.add(cancelButton);

        gbc.gridx = 0;

        gbc.gridy++;

        gbc.gridwidth = 2;

        panel.add(buttons, gbc);

        add(panel);

    }

    //=================================================
    // LISTENERS
    //=================================================

    private void initializeListeners() {

        createButton.addActionListener(e -> createProduct());

        cancelButton.addActionListener(e -> dispose());

    }

    //=================================================
    // CREATE PRODUCT
    //=================================================

    private void createProduct() {

        try {

            String name = nameField.getText().trim();

            String description =
                    descriptionArea.getText().trim();

            float price =
                    Float.parseFloat(priceField.getText());

            boolean available =
                    availabilityCheckBox.isSelected();

            TemperatureOfTheService temperature =
                    (TemperatureOfTheService)
                            temperatureComboBox.getSelectedItem();

            String type =
                    (String) typeComboBox.getSelectedItem();

            CoffeeCountry coffeeCountry = (CoffeeCountry) coffeeCountryComboBox.getSelectedItem();

            if(name.isBlank()){

                JOptionPane.showMessageDialog(

                        this,

                        "Product name cannot be empty."

                );

                return;

            }

            Product product = null;

            switch (type){

                case "Americano":

                    product = new Americano(
                            name,
                            price,
                            available,
                            description,
                            temperature,
                            coffeeCountry
                    );

                    break;

                case "Cafe Latte":

                    product = new CafeLatte(
                            name,
                            price,
                            available,
                            description,
                            temperature,
                            coffeeCountry
                    );

                    break;


            }

            JOptionPane.showMessageDialog(

                    this,

                    "Product created successfully."

            );

            dispose();

        }

        catch (NumberFormatException ex){

            JOptionPane.showMessageDialog(

                    this,

                    "Invalid price."

            );

        }

    }

}