package Models;

import Enums.Size;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Drink extends Product implements Preparable {
    private static final long serialVersionUID = 1L;
    //EXTENT SESSION
    /** Extent session contains:
     * <br>to String method</br>
     * <br>{@linkplain List} in implementation as {@linkplain ArrayList} called extent that holds all instances</br>
     * <br>Private Static method "addDrink" which adds instance of {@linkplain Drink} to extent collection</br>
     * <br>Private Static method "removeDrink" which removes instance of {@linkplain Drink} from extent collection</br>
     * <br>Public Static method "showExtent" which displays all instances of {@linkplain Drink} line by line.</br>
     */
    @Override
    public String toString() {
        return "Drink: " + super.productName  + super.toString()+" " + coffeineContain + " " + size;
    }

    //EXTENT SESSION END
    //FIELDS SESSION START
    boolean coffeineContain;
    Size size;
    //FIELDS SESSION END
    //CONSTRUCTORS, GETTERS, SETTERS SESSION START
    public Drink(String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService) {
        super(name, cost, availability, description, temperatureOfService);
    }
    public Drink(String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService, boolean coffeineContain, Size size) {
        super(name, cost, availability, description, temperatureOfService);
        this.coffeineContain = coffeineContain;
        this.size = size;
    }
    public Drink() {
        super();
    }
    //CONSTRUCTORS, GETTERS, SETTERS SESSION END
    //METHODS SESSION START
    @Override
    public void prepare() {
    }
    @Override
    public int getPreparationTime() {
        return 0;
    }
    //METHODS SESSION END
}