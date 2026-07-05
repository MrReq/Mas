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
                                                                                                                        
                                                                                                                        The first part is more about Use cases and then there is a description of an implementation of duties.
                                                                                                                        
                                                                                                                        ============================================================
                                                                                                                        LOGIN
                                                                                                                        ============================================================
                                                                                                                        Available users:
                                                                                                                        
                                                                                                                        • Boss
                                                                                                                        • Barista
                                                                                                                        • Waiter
                                                                                                                        • Cleaner
                                                                                                                        • Client
                                                                                                                        
                                                                                                                        Employees and Clients can log in only between 08:00 and 20:00.
                                                                                                                        Boos can log in whenever he wants.
                                                                                                                        
                                                                                                                        ============================================================
                                                                                                                        CLIENT
                                                                                                                        ============================================================
                                                                                                                        
                                                                                                                        The client can:
                                                                                                                        
                                                                                                                        • Browse menu (Client Menu Panel)
                                                                                                                        • Add products to shopping cart (Button -> Add to Cart)
                                                                                                                        • Remove products from shopping cart (Button -> Remove)
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
                                                                                                                        NEW -> ACCEPTED->PREPARING->READY->SERVED->COMPLETED
                                                                                                                        ============================================================
                                                                                                                        OBJECT ORIENTED FEATURES
                                                                                                                        ============================================================
                                                                                                                        
                                                                                                                        1.Extension: 
                                                                                                                        Every class inherits from ObjectPlus.
                                                                                                                        Every class has Extension for its own, so implements
                                                                                                                        public static List<Class> getClassExtent() {
                                                                                                                        return (List<Class>) (List<?>) ObjectPlus.getExtent(Class.class);
                                                                                                                        }
                                                                                                                        
                                                                                                                        2.Serialization
                                                                                                                        In ObjectPlus methods: 
                                                                                                                        ObjectPlus.writeExtents() 
                                                                                                                        ObjectPlus.readExtents()
                                                                                                                        
                                                                                                                        3.Composite attribute:
                                                                                                                        Attribute Address as a private field in instances of Client Class.
                                                                                                                        Adress is composite because its Class as its own. Has different fields.
                                                                                                                        
                                                                                                                        4.Optional attribute
                                                                                                                        Address is also an additional Attribute. 
                                                                                                                        Client can modify it whenever he/she wants.
                                                                                                                        
                                                                                                                        5.Repeatable attribute
                                                                                                                        private final List<Product> products = new ArrayList<>();
                                                                                                                        Attribute that contains values that are repeatable.
                                                                                                                        
                                                                                                                        6.Class attribute
                                                                                                                        public static LocalTime start = LocalTime.of(8,0); -> the opening hour of the CoffeeShop
                                                                                                                        
                                                                                                                        7.Derived attribute
                                                                                                                        private float totalPrice -> is changing during preparation of the order by client.
                                                                                                                        Is dependent of clients' activities.
                                                                                                                        
                                                                                                                        8.Class method
                                                                                                                        public static void manageEmployees() - class method from Boss Class it is to manage Employees. 
                                                                                                                        Implementation after clicking the button (ManageEmployeesView) shows Us the Worker who earns the most.
                                                                                                                        
                                                                                                                        9.Override
                                                                                                                        Weell of course in project there are a lot of Overrides.
                                                                                                                        But the best Example is Override of the abstract method from Coffee Class.
                                                                                                                        There are 3 types of Coffee and each implements different way of counting coffeePower.
                                                                                                                        @Override
                                                                                                                        public String countPowerOfCoffee() {
                                                                                                                        return "Power of this coffee is "+shotOfEspressoCount*presure/extractionTime;
                                                                                                                        }
                                                                                                                        
                                                                                                                        10.Overload
                                                                                                                        method addProduct(Product product) is overloaded by method that takes in parameters many of Products
                                                                                                                        method addProduct(Product... product)
                                                                                                                        
                                                                                                                        11.Binary Association: Client 1 -> * Orders.
                                                                                                                        Client can make a lot of orders. But every order belongs to one concrete client.
                                                                                                                        There is reverse connection. In Client we have client.addOrder(Order order) and in Order we have order.setClient(Client client)
                                                                                                                        
                                                                                                                        12.Association with attribute
                                                                                                                        An Example of this association is 
                                                                                                                        
                                                                                                                        13.Qualified Association
                                                                                                                        I tried but I failed
                                                                                                                        
                                                                                                                        14.Composition
                                                                                                                        I tried but I failed.
                                                                                                                        
                                                                                                                        15.Disjoint
                                                                                                                        Most of the inheritance are disjoint. 
                                                                                                                        For example Employees, they can not share duties with others.
                                                                                                                        
                                                                                                                        16.Abstract Class:
                                                                                                                        There are two different which are abstract Person and Coffee
                                                                                                                        
                                                                                                                        17.Polymorphism:
                                                                                                                        Polymorphic method is from coffee objects. CountPowerOfCoffee()
                                                                                                                        
                                                                                                                        18:Multiple Inheritance:
                                                                                                                        -
                                                                                                                        
                                                                                                                        19:Multi-faceted inheritance
                                                                                                                        There are two types of inheritance from Employee depends on duties (Cleaner, Waiter,Barista)
                                                                                                                        and depends on internship (LowExperienced and HighExperienced)
                                                                                                                        
                                                                                                                        20.Dynamic Inheritance:
                                                                                                                        there is a possibility that Employee can become HighExperienced
                                                                                                                        
                                                                                                                        21.Enumerations
                                                                                                                        There are a lot of Enums: AllPersonTypes, Citizenship, CoffeeCountry, Doneness, LanguageSkill, Level,
                                                                                                                        OrderStatus,OrderType,SatisfactionOfTheService, Sex, Shift,Size,TemperatureOfTheService,TypeOfMeal,TypeOfMilk
                                                                                                                        
                                                                                                                        22 Validation Methods:
                                                                                                                        All except Boss can log in between 8 - 20
                                                                                                                        Employee can be promoted only if done at least 6 order.
                                                                                                                        Password Validation
                                                                                                                        
                                                                                                                        ============================================================
                                                                                                                        Thank you!
""");

    }
    // LAYOUT

    private void initializeLayout() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Project Documentation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }
}