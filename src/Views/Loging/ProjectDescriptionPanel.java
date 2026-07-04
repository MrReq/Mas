package Views.Loging;
import javax.swing.*;
import java.awt.*;
public class ProjectDescriptionPanel extends JPanel {
    private JTextArea textArea;
    public ProjectDescriptionPanel() {
        initializeComponents();
        initializeLayout();
    }
    // COMPONENTS
    private void initializeComponents() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        textArea.setText("""
============================================================
                COFFEE HOUSE MANAGEMENT SYSTEM
============================================================
Author:Marcin Ząbkowski
Project:Coffee House Management System

============================================================
PROJECT DESCRIPTION
============================================================

The application simulates the operation of a modern coffee house.

The system allows different users to log into the application,
manage coffee orders, products and employees depending on
their role.

============================================================
LOGIN
============================================================

Available users:

• Boss
• Barista
• Waiter
• Cleaner
• Client

Employees can log in only between 08:00 and 20:00.

============================================================
CLIENT
============================================================

The client can:

• Browse menu (Client Menu Panel)
• Add products to shopping cart (Button -> Add to Cart)
• Remove products from shopping cart (Button ->Remove)
• Place order (Button -> Place Order)
• View current orders (Tab -> My Orders )
• View order history (Tab -> My Orders )
• View profile (Tab -> Profile)

============================================================
BARISTA
============================================================

The barista can:

• Accept NEW orders (Button -> Accept)
• Prepare coffee (Button -> Prepare Coffee)
• Mark order as READY (Button -> Coffee Ready)
• View accepted orders 
• View finished orders
• Display statistics

Preparation is implemented using
an Association Class (Preparation).

============================================================
WAITER
============================================================

The waiter can:

• Receive READY orders
• Serve customers
• View served orders

============================================================
CLEANER
============================================================

The cleaner can:

• Clean tables
• View dirty tables
• Mark tables as clean

============================================================
BOSS
============================================================

The boss can:

EMPLOYEES

• Add employee (Button -> Add Employee (ManageEmployeeView))
• Edit employee 
• Remove employee (Button)

PRODUCTS

• Add products
• Edit products
• Remove products

STATISTICS

• Number of clients
• Number of employees
• Number of orders
• Most expensive product
• Total income

============================================================
ORDER LIFE CYCLE
============================================================

Shopping Cart

↓

NEW

↓

ACCEPTED

↓

PREPARING

↓

READY

↓

SERVED

↓

COMPLETED

============================================================
OBJECT ORIENTED FEATURES
============================================================

✔ Inheritance

✔ Abstract classes

✔ Interfaces

✔ Composition

✔ Association

✔ Association Class

✔ Extents

✔ Enumerations

✔ Business methods

✔ Static factory methods

✔ Serialization

✔ Validation

✔ Swing GUI

============================================================
Thank you for using Coffee House Management System.
============================================================
""");

    }

    //=================================================
    // LAYOUT
    //=================================================

    private void initializeLayout() {

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "Project Documentation",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane, BorderLayout.CENTER);

    }

}