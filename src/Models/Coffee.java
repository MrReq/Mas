package Models;

import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public abstract class Coffee extends Drink {
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addCoffee" which adds instance of {@linkplain Coffee} to extent collection</br>
     * <br>Private Static method "removeCoffee" which removes instance of {@linkplain Coffee} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Coffee} line by line.</br>
     */
    @Override
    public String toString() {
        return "Coffee: " + super.productName + ", id: " + super.toString();
    }
    private static List<Coffee> extent = new ArrayList<>();
    private static void addCoffee(Coffee coffee) {
        extent.add(coffee);
    }
    private static void removeCoffee(Coffee coffee) {
        extent.remove(coffee);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Coffee.class.getName());
        for (Coffee coffee : extent) {
            System.out.println(coffee);
        }
    }
    protected void write(DataOutputStream stream) throws IOException {
        super.write(stream);
        stream.writeUTF(coffeeCountry != null ? coffeeCountry.toString() : "");
    }
    protected void read(DataInputStream stream) throws IOException {
        super.read(stream);
        String coffeeCountryString = stream.readUTF();
        coffeeCountry = coffeeCountryString.isEmpty() ? null : CoffeeCountry.valueOf(coffeeCountryString);
    }
    public static void writeExtent(DataOutputStream stream) throws IOException {
        // Number of objects
        stream.writeInt(extent.size());
        for (Coffee movie : extent) {
            movie.write(stream);
        }
    }
    public static void readExtent(DataInputStream stream) throws IOException {
        int objectCount = stream.readInt();
        extent.clear();
        for (int i = 0; i < objectCount; i++) {
            String type = stream.readUTF();
            Coffee coffee;
            switch (type) {
                case "Americano":
                    coffee = new Americano();
                    break;
                case "Latte":
                    coffee = new CafeLatte();
                    break;
                default:
                    throw new IOException("Unknown coffee type: " + type);
            }
            coffee.read(stream);
            extent.add(coffee);
        }
    }
    //EXTENT SESSION END

    //FIELDS SESSION START
    /**Simple, Single, Required, Object, Concrete Attribute "coffeeCountry" typed {@linkplain CoffeeCountry}
     */
    CoffeeCountry coffeeCountry;
    double power;
    double waterToCoffeeRatio;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Coffee() {
        super();
    }
    public Coffee(String name, float cost, boolean availability, String description,
                  TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService);
        this.coffeeCountry=coffeeCountry;
        addCoffee(this);
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END

    //METHODS SESSION START
    public String countPowerOfCoffee(){
        if (this.coffeeCountry== CoffeeCountry.Arabia){
            this.power =  (1 - waterToCoffeeRatio + 0.3);
        }else if(this.coffeeCountry== CoffeeCountry.Kenia){
            this.power =  (1 - waterToCoffeeRatio + 0.38);
        }else {
            this.power =  (1 - waterToCoffeeRatio + 0.17);
        }
        return "Power of the coffee is "+power;
    }
    //METHODS SESSION END
}
