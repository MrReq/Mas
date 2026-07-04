package Views.Loging;
import SecondaryClasses.ObjectPlus;
import javax.swing.*;
import java.awt.*;
public class LoginSelectionView extends JFrame {
    private JTabbedPane tabbedPane;
    public LoginSelectionView() {
        setTitle("Coffee House - Logowanie (LoginSelectionView)");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        JLabel title = new JLabel("Wybierz sposób logowania", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        JButton clientButton = new JButton("Zaloguj jako klient");
        JButton employeeButton = new JButton("Zaloguj jako pracownik");
        JButton createBossButton = new JButton("Utwórz szefa");
        JButton clearextensionButton = new JButton("Wyczyśc całą ekstensję");
        JButton exitProgram = new JButton("Wyłącz Program");
        buttonsPanel.add(clientButton);
        buttonsPanel.add(employeeButton);
        buttonsPanel.add(createBossButton);
        buttonsPanel.add(clearextensionButton);
        buttonsPanel.add(exitProgram);
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab(
                "Project Description",
                new ProjectDescriptionPanel()
        );

        tabbedPane.addTab(
                "Login",
                 panel
        );
        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        add(tabbedPane);
        // Obsługa przycisków
        clientButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Wybrano logowanie jako klient.");
            new LoginOrRegisterView().setVisible(true);
        });
        employeeButton.addActionListener(e -> {
            dispose(); // zamyka aktualne okno
            new EmployeeRoleSelectionView().setVisible(true);
        });
        createBossButton.addActionListener(e -> {
            dispose();
            new CreateBossView().setVisible(true);
        });
        clearextensionButton.addActionListener(e -> {
            ObjectPlus.clearExtents();
        });
        exitProgram.addActionListener(e-> {
            exitApplication();
        });
    }
    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to exit the application?",
                "Exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}