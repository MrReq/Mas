package Models;

import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Americano extends Coffee implements Preparable {
    private static final long serialVersionUID = 1L;
    public Americano(String name, float price, boolean available, String description, TemperatureOfTheService temperature) {
    }

    public Americano() {

    }
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addAmericano" which adds instance of {@linkplain Americano} to extent collection</br>
     * <br>Private Static method "removeAmericano" which removes instance of {@linkplain Americano} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Americano} line by line.</br>
     */
    @Override
    public String toString() {
        return "Americano: " + super.productName + ", id: " + super.toString();
    }
    private static List<Americano> extent = new ArrayList<>();
    private static void addAmericano(Americano americano) {
        extent.add(americano);
    }
    private static void removeAmericano(Americano americano) {
        extent.remove(americano);
    }
    public static void showExtent() {
        System.out.println("Extent of the class: " + Americano.class.getName());
        for (Americano americano : extent) {
            System.out.println(americano);
        }
    }
    protected void write(DataOutputStream stream) throws IOException {
        stream.writeDouble(waterAmount);
        stream.writeInt(espressoShotCount);
        stream.writeDouble(waterAmount);
        stream.writeDouble(power);
    }
    /**
     * @param stream
     * @throws IOException
     */
    protected void read(DataInputStream stream) throws IOException {
        waterAmount = stream.readDouble();
        espressoShotCount = stream.readInt();
        waterToCoffeeRatio = stream.readDouble();
        power = stream.readDouble();
    }
    public static void writeExtent(DataOutputStream stream) throws IOException {
        // Number of objects
        stream.writeInt(extent.size());
        for (Americano americano : extent) {
            americano.write(stream);
        }
    }
    public static void readExtent(DataInputStream stream) throws IOException {
        Americano americano = null;
        // Get the number of written objects
        int objectCount = stream.readInt();
        // remove the current extent
        extent.clear();
        for (int i = 0; i < objectCount; i++) {
//            americano = new Americano();
            americano.read(stream);
        }
    }
    //EXTENT SESSION END
    //FIELDS SESSION START
    /**Simple, Single, Required, Object, Concrete, Attribute "waterAmount" typed {@linkplain Double}
     */
    double waterAmount;
    /**Simple, Single, Required, Object, Concrete, Attribute "espressoShotCount" typed {@linkplain Integer}
     */
    int espressoShotCount;
    /**Simple, Single, Required, Object, Concrete, Attribute "waterToCoffeeRatio" typed {@linkplain Double}
     */
    double waterToCoffeeRatio;
    /**Simple, Single, Required, Object, Concrete, Attribute "power" typed {@linkplain Double}
     */
    double power;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
//    public Americano() {
//        super();
//    }
    public Americano(String name, float cost, boolean availability, String description
            , TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END
    //METHODS SESSION START
    /**
     * Public object method "countPowerOfCoffee"
     * @param : none
     * @return : power of the coffee which depends on {@linkplain CoffeeCountry}
     */
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

    @Override
    public void prepare() {
        System.out.println("Brewing Americano...");
    }
    //METHODS SESSION END
}