package Models;
import Enums.CoffeeCountry;
import Enums.TemperatureOfTheService;
import Interfaces.Preparable;
public class Espresso extends Coffee  implements Preparable {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Espresso: " + productName + ", id: " + super.toString();
    }
    int shotOfEspressoCount;
    int extractionTime;
    double presure;
    public Espresso(String name, float cost, boolean availability, String description
            , TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
    }
    public Espresso(String name, float cost, boolean availability, String description
            , TemperatureOfTheService temperatureOfService, CoffeeCountry coffeeCountry, int shotOfEspressoCount
            , int extractionTime, double presure) {
        super(name, cost, availability, description, temperatureOfService, coffeeCountry);
        this.shotOfEspressoCount = shotOfEspressoCount;
        this.extractionTime = extractionTime;
        this.presure = presure;
    }
    @Override
    public String countPowerOfCoffee() {
        return "Power of this coffee is "+shotOfEspressoCount*presure/extractionTime;
    }


}
