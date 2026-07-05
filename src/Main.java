import Models.Employee;
import SecondaryClasses.ObjectPlus;
import Models.Order;
import Views.Loging.LoginSelectionView;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import Models.*;
import Views.Loging.ProjectDescriptionPanel;

public class Main {
    private static final String FILE_NAME = "extents.dat";
    public static void main(String[] args) {

        new ProjectDescriptionPanel().setVisible(true);
        loadApplication();
        for (Client client : Client.getClientExtent()) {
            Order cart = client.getShoppingCart();

            System.out.println(
                    client.getPersonName()
                            + " -> shoppingCart = "
                            + (cart == null ? "null" : cart.getOrderID())
                            + ", products = "
                            + (cart == null ? "-" : cart.getProducts().size())
            );
        }
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveApplication));
        SwingUtilities.invokeLater(() -> new LoginSelectionView().setVisible(true));
    }
    // LOAD
    private static void loadApplication() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("First application start.");
            return;
        }
        try {
            ObjectPlus.loadExtents(FILE_NAME);
            Order.rebuildCounter();
            Employee.employeerebuildCounter();
            Product.productRebuildCounter();
            System.out.println("Extents loaded.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load extents.");
            e.printStackTrace();
        }
    }
    // SAVE
    private static void saveApplication() {
        try {
            ObjectPlus.saveExtents(FILE_NAME);
            System.out.println("All extents saved.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}