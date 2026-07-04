package Views.Loging;
import Enums.CoffeeCountry;
import Enums.Sex;
import Models.Barista;
import Models.Boss;
import Views.Barista.BaristaDashboardView;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
public class BaristaLoginView extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JSpinner birthDateSpinner;
    private JComboBox<Sex> sexComboBox;
    private JComboBox<CoffeeCountry> coffeeCountryComboBox;
    private JButton loginButton;
    private JButton backButton;
    public BaristaLoginView() {
        initializeFrame();
        initializeComponents();
        initializeLayout();
        initializeListeners();
    }
    // FRAME
    private void initializeFrame() {
        setTitle("Coffee House - Barista Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // COMPONENTS
    private void initializeComponents() {
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd"));
        sexComboBox = new JComboBox<>(Sex.values());
        coffeeCountryComboBox = new JComboBox<>(CoffeeCountry.values());
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
    }
    // LAYOUT
    private void initializeLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel title = new JLabel("BARISTA LOGIN");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title,gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("First name:"),gbc);
        gbc.gridx = 1;
        panel.add(firstNameField,gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Last name:"),gbc);
        gbc.gridx = 1;
        panel.add(lastNameField,gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Birth date:"),gbc);
        gbc.gridx = 1;
        panel.add(birthDateSpinner,gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sex:"),gbc);
        gbc.gridx = 1;
        panel.add(sexComboBox,gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Favourite coffee country:"),gbc);
        gbc.gridx = 1;
        panel.add(coffeeCountryComboBox,gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(loginButton,gbc);
        gbc.gridx = 1;
        panel.add(backButton,gbc);
        add(panel);
    }
    // LISTENERS
    private void initializeListeners() {
        loginButton.addActionListener(e -> login());
        backButton.addActionListener(e -> {
            dispose();
            new TypeOfEmployeeLoginView().setVisible(true);});
    }
    // LOGIN
    private void login() {
        String name = firstNameField.getText().trim();
        String surname = lastNameField.getText().trim();
        Date date = (Date) birthDateSpinner.getValue();
        LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Sex sex = (Sex) sexComboBox.getSelectedItem();
        CoffeeCountry country = (CoffeeCountry) coffeeCountryComboBox.getSelectedItem();
        LocalTime now = LocalTime.now();
        if (now.isBefore(Boss.start) || now.isAfter(Boss.end)) {
            JOptionPane.showMessageDialog(this, "Employees can log in only between 08:00 and 20:00.", "Login denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Barista barista : Barista.getBaristaExtent()) {
            if (barista.getPersonName().equalsIgnoreCase(name) && barista.getPeronSurname().equalsIgnoreCase(surname)
                    && barista.getPersonDateOfBirth().equals(birthDate)
                    && barista.getPersonSex() == sex && barista.getFavouriteCoffeeCountry() == country) {
                JOptionPane.showMessageDialog(this, "Login successful.");
                dispose();
                new BaristaDashboardView(barista).setVisible(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Barista not found.", "Login error", JOptionPane.ERROR_MESSAGE);
    }
}