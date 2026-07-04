package Views.Barista;
import Models.Barista;
import javax.swing.*;
import java.awt.*;
public class BaristaStatisticsPanel extends JPanel {
    private final Barista loggedBarista;
    private JLabel preparingCoffeeLabel;
    private JLabel waitingOrdersLabel;
    private JLabel completedOrdersLabel;
    private JLabel workedHoursLabel;
    private JLabel favouriteCoffeeCountryLabel;
    private JButton refreshButton;
    public BaristaStatisticsPanel(Barista loggedBarista) {
        this.loggedBarista = loggedBarista;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshStatistics();
    }
    // COMPONENTS
    private void initializeComponents() {
        preparingCoffeeLabel = new JLabel();
        waitingOrdersLabel = new JLabel();
        completedOrdersLabel = new JLabel();
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
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new GridLayout(6,2,10,10));
        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        statisticsPanel.add(new JLabel("Barista:"));
        statisticsPanel.add(new JLabel(loggedBarista.getPersonName() + " " + loggedBarista.getPeronSurname()));
        statisticsPanel.add(new JLabel("Prepared coffees:"));
        statisticsPanel.add(preparingCoffeeLabel);
        statisticsPanel.add(new JLabel("Waiting orders:"));
        statisticsPanel.add(waitingOrdersLabel);
        statisticsPanel.add(new JLabel("Completed orders:"));
        statisticsPanel.add(completedOrdersLabel);
        statisticsPanel.add(new JLabel("Worked hours:"));
        statisticsPanel.add(workedHoursLabel);
        statisticsPanel.add(new JLabel("Favourite coffee country:"));
        statisticsPanel.add(favouriteCoffeeCountryLabel);
        add(statisticsPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    // LISTENERS
    private void initializeListeners() {
        refreshButton.addActionListener(e -> refreshStatistics());
    }
    // REFRESH
    private void refreshStatistics() {
        preparingCoffeeLabel.setText(String.valueOf(loggedBarista.countPreparingOrders()));
        waitingOrdersLabel.setText(String.valueOf(loggedBarista.countNewOrders()));
        completedOrdersLabel.setText(String.valueOf(loggedBarista.countReadyOrders()));
        workedHoursLabel.setText("Its hard to implement this one");
        if(loggedBarista.getFavouriteCoffeeCountry() != null){
            favouriteCoffeeCountryLabel.setText(loggedBarista.getFavouriteCoffeeCountry().name());
        }else{
            favouriteCoffeeCountryLabel.setText("Not specified");
        }
    }
}