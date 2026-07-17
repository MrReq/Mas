package Models;
import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;
import SecondaryClasses.ObjectPlus;
import java.util.List;
public class Americano extends Coffee implements Preparable {
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")
    public static List<Americano> getBaristaExtent() {
        return (List<Americano>) (List<?>) ObjectPlus.getExtent(Americano.class);
    }
    @Override
    public String toString() {
        return "Americano: " + super.productName + ", id: " + super.toString();
    }
    double waterAmount;
    int espressoShotCount;
    double waterToCoffeeRatio;
    public Americano(String name, float cost, boolean availability, String description
                    ,TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
    }
    public Americano(String name, float cost, boolean availability, String description
            , TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry, double waterAmount
            , int espressoShotCount, double waterToCoffeeRatio) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
        this.waterAmount = waterAmount;
        this.espressoShotCount = espressoShotCount;
        this.waterToCoffeeRatio = waterToCoffeeRatio;
    }
    public String countPowerOfCoffee(){
        if (this.coffeeCountry== CoffeeCountry.Arabia)
            this.power =  (1 - waterToCoffeeRatio + 0.3);
        else if(this.coffeeCountry== CoffeeCountry.Kenia)
            this.power =  (1 - waterToCoffeeRatio + 0.38);
        else
            this.power =  (1 - waterToCoffeeRatio + 0.17);
        return "Power of the coffee is "+power;
    }
    @Override
    public void prepare() {
        System.out.println("Brewing Americano...");
    }

}