package Views.Waiter;
import Models.Waiter;
import javax.swing.*;
import java.awt.*;
public class WaiterStatisticsPanel extends JPanel {
    private final Waiter loggedwaiter;
    private JLabel servedTables;
    private JLabel completedOrders;
    private JLabel tips;
    private JLabel averageGrade;
    private JLabel workedHours;
    private JButton refreshButton;
    public WaiterStatisticsPanel(Waiter loggedwaiter) {
        this.loggedwaiter = loggedwaiter;
        initializeComponents();
        initializeLayout();
        initializeListeners();
        refreshStatistics();
    }

    private void initializeComponents() {
        servedTables = new JLabel();
        completedOrders = new JLabel();
        tips = new JLabel();
        averageGrade = new JLabel();
        workedHours = new JLabel();
        refreshButton = new JButton("Refresh");
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Waiter Statistics", SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,24));
        add(title,BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(6,2,10,10));
        center.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
        center.add(new JLabel("Waiter:"));
        center.add(new JLabel(loggedwaiter.getPersonName()+" "+loggedwaiter.getPeronSurname()));
        center.add(new JLabel("Served Tables:"));
        center.add(servedTables);
        center.add(new JLabel("Completed Orders:"));
        center.add(completedOrders);
        center.add(new JLabel("Tips:"));
        center.add(tips);
        center.add(new JLabel("Average Grade:"));
        center.add(averageGrade);
        center.add(new JLabel("Worked Hours:"));
        center.add(workedHours);
        add(center,BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(refreshButton);
        add(bottom,BorderLayout.SOUTH);
    }
    private void initializeListeners() {
        refreshButton.addActionListener(
                e -> refreshStatistics()
        );
    }
    private void refreshStatistics() {
//        servedTables.setText(loggedwaiter.);
        completedOrders.setText("132");
        tips.setText("520 PLN");
        averageGrade.setText("0");
        workedHours.setText("0 h");
    }
//    public void reload(){
//        refreshTable();
//    }
}