package Views.Barista;
import Models.Barista;
import javax.swing.*;
import java.awt.*;
public class BaristaStatisticsPanel extends JPanel {
    private final Barista loggedBarista;
    private JLabel NewOrdersLabel;
    private JLabel AcceptedCoffeeLabel;
    private JLabel PreparingOrdersLabel;
    private JLabel ReadyOrdersLabel;
    private JLabel workedHoursLabel;
    private JLabel favouriteCoffeeCountryLabel;
    private JButton refreshButton;
    private BaristaDashboardView parent;
    public BaristaStatisticsPanel(Barista loggedBarista, BaristaDashboardView parent) {
        this.loggedBarista = loggedBarista;
        this.parent = parent;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshStatistics();
    }
    // COMPONENTS
    private void initializeComponents() {
        NewOrdersLabel = new JLabel();
        PreparingOrdersLabel = new JLabel();
        AcceptedCoffeeLabel = new JLabel();
        ReadyOrdersLabel = new JLabel();
        workedHoursLabel = new JLabel();
        favouriteCoffeeCountryLabel = new JLabel();
        refreshButton = new JButton("Refresh");
    }
    // LAYOUT
    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Barista Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;
        addRow(center, gbc, row++, "Barista:", loggedBarista.getPersonName() + " " + loggedBarista.getPeronSurname());
        addRow(center, gbc, row++, "NEW orders:", NewOrdersLabel);
        addRow(center, gbc, row++, "ACCEPTED orders:", AcceptedCoffeeLabel);
        addRow(center, gbc, row++, "PREPARING orders:", PreparingOrdersLabel);
        addRow(center, gbc, row++, "READY orders:", ReadyOrdersLabel);
        addRow(center, gbc, row++, "Worked hours:", workedHoursLabel);
        addRow(center, gbc, row++, "Favourite coffee country:", favouriteCoffeeCountryLabel);
        add(center, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        add(bottom, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshStatistics());
    }
    // REFRESH
    private void refreshStatistics() {
        NewOrdersLabel.setText(String.valueOf(loggedBarista.countNewOrders()));
        AcceptedCoffeeLabel.setText(String.valueOf(loggedBarista.countAcceptedOrders()));
        PreparingOrdersLabel.setText(String.valueOf(loggedBarista.countPreparingOrders()));
        ReadyOrdersLabel.setText(String.valueOf(loggedBarista.countReadyOrders()));
        workedHoursLabel.setText(String.valueOf(loggedBarista.getCurrentEmployment().getEmploymentPeriod()));
        if(loggedBarista.getFavouriteCoffeeCountry() != null){
            favouriteCoffeeCountryLabel.setText(loggedBarista.getFavouriteCoffeeCountry().name());
        }else{
            favouriteCoffeeCountryLabel.setText("Not specified");
        }
    }
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(value, gbc);
    }
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(value), gbc);
    }
    public void reload() {
        refreshStatistics();
    }
}