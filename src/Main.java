import Enums.CoffeeCountry;
import Enums.OrderType;
import Enums.Sex;
import Enums.TemperatureOfTheService;
import Models.*;
import SecondaryClasses.ObjectPlus;
import Views.Loging.LoginSelectionView;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        final String FILE_NAME = "extents.dat";

        File file = new File(FILE_NAME);

        try {

            if (file.exists() && file.length() > 0) {

                ObjectPlus.loadExtents(FILE_NAME);
                Order.rebuildCounter();

                System.out.println("Extents loaded.");

                System.out.println(ObjectPlus.getRegisteredClasses());

                System.out.println(
                        ObjectPlus.getExtent(Espresso.class).size()
                );

                System.out.println(
                        ObjectPlus.getExtent(Americano.class).size()
                );

                System.out.println(
                        ObjectPlus.getExtent(CafeLatte.class).size()
                );

            } else {

                System.out.println("First application start.");

                generateSampleData();

                ObjectPlus.saveExtents(FILE_NAME);

                System.out.println("Sample data generated.");

            }

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

        }

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {

                    try {

                        ObjectPlus.saveExtents(FILE_NAME);

                        System.out.println("All extents saved.");

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                })
        );

        SwingUtilities.invokeLater(() ->
                new LoginSelectionView().setVisible(true)
        );
    }
    private static void generateSampleData() {
        System.out.println("=== GENERATING SAMPLE DATA ===");
        //=================================================
        // CLIENTS
        //=================================================

        Client client1 = new Client(
                "Jan",
                "Kowalski",
                LocalDate.of(1995, 5, 12),
                Sex.Male,true
        );

        Client client2 = new Client(
                "Anna",
                "Nowak",
                LocalDate.of(1998, 8, 20),
                Sex.Female,true
        );

        Client client3 = new Client(
                "Piotr",
                "Wiśniewski",
                LocalDate.of(1992, 2, 18),
                Sex.Male,false
        );

        //=================================================
        // PRODUCTS
        //=================================================

        Product latte = new CafeLatte(
                "Cafe Latte",
                18.50f,
                true,
                "Italian latte",
                TemperatureOfTheService.Hot,
                CoffeeCountry.Kenia
        );

        Product americano = new Americano(
                "Americano",
                15.00f,
                true,
                "Classic americano",
                TemperatureOfTheService.Hot
        );



        //=================================================
        // ORDERS
        //=================================================

        Order order1 =
                Order.createOrder(client1, OrderType.Liquid);

        order1.addProduct(latte);





        Order order2 =
                Order.createOrder(client2, OrderType.Liquid);

        order2.addProduct(americano);


        Order order3 =
                Order.createOrder(client3, OrderType.Liquid);

        order3.addProduct(latte);

        order3.startPreparation();



        Order order4 =
                Order.createOrder(client1, OrderType.Liquid);

        order4.addProduct(americano);

        order4.markAsReady();



        Order order5 =
                Order.createOrder(client2, OrderType.Liquid);

        order5.addProduct(latte);

        order5.cancelOrder();

        System.out.println("Orders: " + Order.getOrderExtent().size());
        System.out.println("Clients: " + Client.getClientExtent().size());
        System.out.println("Products: " + Product.getProductExtent().size());
    }
}