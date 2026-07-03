package Models;

import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public abstract class Coffee extends Drink implements Preparable {
    private static final long serialVersionUID = 1L;
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
