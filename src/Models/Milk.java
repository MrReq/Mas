package Models;
import Enums.TemperatureOfTheService;
import Enums.TypeOfMilk;
import SecondaryClasses.ObjectPlus;
import java.util.List;
public class Milk extends Drink {
    private static final long serialVersionUID = 1L;
    private TypeOfMilk typeOfMilk;
    public Milk(String name, float cost, boolean availability, String description, TemperatureOfTheService temperatureOfService) {
        super(name, cost, availability, description, temperatureOfService);
    }
    @SuppressWarnings("unchecked")
    public static List<Milk> getMilkExtent() {
        return (List<Milk>) (List<?>) ObjectPlus.getExtent(Milk.class);
    }

    public TypeOfMilk getTypeOfMilk() {
        return typeOfMilk;
    }
    public void setTypeOfMilk(TypeOfMilk typeOfMilk) {
        this.typeOfMilk = typeOfMilk;
    }
    @Override
    public String toString() {
        return "Milk{" + "name='" + productName + '\'' + ", typeOfMilk=" + typeOfMilk + '}';
    }
}