package Models;
import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import SecondaryClasses.ObjectPlus;

import java.util.ArrayList;
import java.util.List;
public abstract class Coffee extends Drink  {
    private static final long serialVersionUID = 1L;
    //EXTENT SESSION
    @SuppressWarnings("unchecked")
    public static List<Coffee> getCleanerExtent() {
        return (List<Coffee>) (List<?>) ObjectPlus.getExtent(Coffee.class);
    }
    @Override
    public String toString() {
        return "Coffee: " + super.productName + ", id: " + super.toString();
    }
    CoffeeCountry coffeeCountry;
    double power;
    public Coffee() {
        super();
    }
    public Coffee(String name, float cost, boolean availability, String description,
                  TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService);
        this.coffeeCountry=coffeeCountry;

    }
    public abstract Object countPowerOfCoffee();
}
