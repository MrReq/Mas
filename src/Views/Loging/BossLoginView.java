package Views.Loging;

import Models.Boss;
import Views.Boss.BossDashboardView;
import Views.Loging.LoginSelectionView;

import javax.swing.*;
import java.awt.*;

public class BossLoginView extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton backButton;

    public BossLoginView() {

        initializeFrame();

        initializeComponents();

        layoutComponents();

        registerListeners();

    }

    private void initializeFrame() {

        setTitle("Coffee House - Owner Login (BossLoginView)");
        setSize(450,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private void initializeComponents() {

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Zaloguj");
        backButton = new JButton("Powrót");

    }

    private void layoutComponents() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title =
                new JLabel("LOGOWANIE WŁAŚCICIELA");

        title.setHorizontalAlignment(SwingConstants.CENTER);

        title.setFont(new Font("Arial",Font.BOLD,20));

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;

        panel.add(title,gbc);

        gbc.gridwidth=1;

        gbc.gridy++;

        panel.add(new JLabel("Imię"),gbc);

        gbc.gridx=1;

        panel.add(firstNameField,gbc);

        gbc.gridx=0;

        gbc.gridy++;

        panel.add(new JLabel("Nazwisko"),gbc);

        gbc.gridx=1;

        panel.add(lastNameField,gbc);

        gbc.gridx=0;

        gbc.gridy++;

        panel.add(new JLabel("Hasło"),gbc);

        gbc.gridx=1;

        panel.add(passwordField,gbc);

        gbc.gridy++;

        gbc.gridx=0;

        panel.add(loginButton,gbc);

        gbc.gridx=1;

        panel.add(backButton,gbc);

        add(panel);

    }

    private void registerListeners(){

        loginButton.addActionListener(e->login());

        backButton.addActionListener(e->{

            dispose();

            new LoginSelectionView().setVisible(true);

        });

    }

    private void login(){

        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if(firstName.isBlank()
                || lastName.isBlank()
                || password.isBlank()){

            JOptionPane.showMessageDialog(

                    this,

                    "Wypełnij wszystkie pola.",

                    "Błąd",

                    JOptionPane.ERROR_MESSAGE

            );

            return;

        }

        for(Boss boss : Boss.getBossExtent()){

            if(boss.getPersonName().equalsIgnoreCase(firstName)
                    &&
                    boss.getPeronSurname().equalsIgnoreCase(lastName)
                    &&
                    boss.getPassword().equals(password)){

                JOptionPane.showMessageDialog(

                        this,

                        "Logowanie zakończone sukcesem."

                );

                dispose();

                new BossDashboardView(boss).setVisible(true);

                return;

            }

        }

        JOptionPane.showMessageDialog(

                this,

                "Niepoprawne dane logowania.",

                "Błąd",

                JOptionPane.ERROR_MESSAGE

        );

        passwordField.setText("");

    }

}